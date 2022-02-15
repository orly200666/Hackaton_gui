package com.mprv.audit_recorder;

import com.mprv.automation.core.exceptions.AutomationException;
import com.mprv.automation.machines.Setup;
import com.mprv.automation.services.SecureSphereServices.audit.DBAuditDataService;
import com.mprv.automation.services.SecureSphereServices.policies.AuditPolicyDO;
import com.mprv.automation.services.SecureSphereServices.policies.AuditPolicyService;
import com.mprv.automation.services.internal.DefaultsService;
import com.mprv.automation.services.internal.SetupsService;
//import com.mprv.monitoring.MonitoredEventReporter;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Date;

/**
 * Created by vladislavs on 12/21/2017.
 */
public class CleanAudit implements Runnable {
    static Setup setup = null;
    static AuditPolicyDO policy = null;
    private JTextPane textArea1;
    private JTextField policyName;
    private JTextField password;
    private JTextField ip;
    private JButton cleanBtn;
    public boolean isRunning = true;
    private JButton runBtn;
    public CleanAudit(JTextField ip, JTextPane textArea, JTextField policyName, JTextField password, JButton cleanBtn, JButton runBtn){
        this.ip = ip;
        this.textArea1 = textArea;
        this.policyName = policyName;
        this.password = password;
        this.cleanBtn = cleanBtn;
        this.runBtn = runBtn;
    }

    private void initPassword(String genericPassword) {
        DefaultsService.setHardeningPassword(genericPassword);
        DefaultsService.setGwSshPassword(genericPassword);
        DefaultsService.setGwSslPassword(genericPassword);
        DefaultsService.setMxSshPassword(genericPassword);
        DefaultsService.setMxGuiPassword(genericPassword);
        DefaultsService.setMxSecurePassword(genericPassword);
        DefaultsService.setMxSystemPassword(genericPassword);
    }

    @Override
    public void run() {
        cleanBtn.setEnabled(false);
        runBtn.setEnabled(false);
//        MonitoredEventReporter monitoredEventReporter = new MonitoredEventReporter();
//        monitoredEventReporter.register();
        initPassword(password.getText());
        SetupsService setupsService = new SetupsService();
        try {
            setup = setupsService.getSetupFromIPs(ip.getText(), new String[]{}, null);
        } catch (AutomationException | ArrayIndexOutOfBoundsException e) {
            AppendText.appendToPane(textArea1, "Connect to MX fail with exception: " + e.getMessage() + " "
                    + new Date(), Color.RED);
            return;
        }
        AuditPolicyService ps = new AuditPolicyService();
        try {
            policy = ps.getPolicyByNameWithPredicates(setup.getPrimaryServer(), policyName.getText(), true);
        } catch (AutomationException e1) {
            AppendText.appendToPane(textArea1, "Get policy by name fail with exception: " + e1.getDetailedMessage() +
                    " \n" + new Date(), Color.RED);
        }
        try {
            if(policy != null){
                AppendText.appendToPane(textArea1, "Starting to clean audit" + " " + new Date(), Color.BLACK);
                new DBAuditDataService().purgeAuditNow(setup.getPrimaryServer(), policy);
                AppendText.appendToPane(textArea1, "SUCCESS" , Color.GREEN);
            }else{
                AppendText.appendToPane(textArea1, "Unable to get audit policy with name: " + policyName.getText() + " \n"
                        + new Date(), Color.RED);
            }
        } catch (AutomationException e1) {
            AppendText.appendToPane(textArea1, "Clean audit fail with exception: " + e1.getDetailedMessage() + " \n"
                    + new Date(), Color.RED);
        }
        setup = null;
        policy = null;
        System.gc();
        cleanBtn.setEnabled(true);
        runBtn.setEnabled(true);
        isRunning = false;
    }
}
