package com.mprv.audit_recorder;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mprv.automation.core.exceptions.AutomationException;
import com.mprv.automation.machines.Setup;
import com.mprv.automation.operations.postJelly.eAuditPhaseTwo_parameters;
import com.mprv.automation.services.SecureSphereServices.audit.AuditFilterDO;
import com.mprv.automation.services.SecureSphereServices.audit.AuditRawDO;
import com.mprv.automation.services.SecureSphereServices.policies.AuditPolicyDO;
import com.mprv.automation.services.SecureSphereServices.policies.AuditPolicyService;
import com.mprv.automation.services.internal.DefaultsService;
import com.mprv.automation.services.internal.SetupsService;
//import com.mprv.monitoring.MonitoredEventReporter;
import com.mprv.systemtests.securesphere.utils.AuditUtils;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.json.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Infra implements Runnable{
    static Setup setup = null;
    static AuditPolicyDO policy = null;
    private String xmlFile;
    private String xmlPath;
    private JTextPane textArea1;
    private JTextField policyName;
    private JTextField password;
    private JTextField ip;
    private JButton runBtn;
    private JButton cleanBtn;
    public boolean isRunning = true;

    public Infra(JTextPane textArea, JTextField policyName, JTextField password, JTextField ip, String xmlPath, String xmlFile, JButton runBtn, JButton cleanBtn){
        this.textArea1 = textArea;
        this.policyName = policyName;
        this.password = password;
        this.ip = ip;
        this.xmlPath = xmlPath;
        this.xmlFile = xmlFile;
        this.runBtn = runBtn;
        this.cleanBtn = cleanBtn;


    }
//    public void createJsonData() {
//        MonitoredEventReporter monitoredEventReporter = new MonitoredEventReporter();
//        monitoredEventReporter.register();
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        initPassword(password.getText());
//        SetupsService setupsService = new SetupsService();
//        try {
//            setup = setupsService.getSetupFromIPs(ip.getText(), new String[]{}, null);
//        } catch (AutomationException | ArrayIndexOutOfBoundsException e) {
//            appendToPane(textArea1, "Connect to MX fail with exception: " + e.getMessage(), Color.RED);
//            return;
//        }
//        create_json_audit();
//    }

    private void initPassword(String genericPassword) {
//        DefaultsService.setHardeningPassword(genericPassword);
//        DefaultsService.setGwSshPassword(genericPassword);
//        DefaultsService.setGwSslPassword(genericPassword);
        DefaultsService.setMxSshPassword(genericPassword);
        DefaultsService.setMxGuiPassword(genericPassword);
        DefaultsService.setMxSecurePassword(genericPassword);
        DefaultsService.setMxSystemPassword(genericPassword);
    }

    private void create_xml_audit() throws ParserConfigurationException {

        AuditPolicyService ps = new AuditPolicyService();
        try {
            policy = ps.getPolicyByNameWithPredicates(setup.getPrimaryServer(), policyName.getText(), true);
//            policy = ps.getPolicyByName(setup.getPrimaryServer(), policyName.getText());
        } catch (AutomationException | NullPointerException e) {
            AppendText.appendToPane(textArea1, "Get policy by name fail with exception: " + e.getMessage() + " " + new Date(), Color.RED);
            return;
        }
        if (policy != null) {
            try {
                AppendText.appendToPane(textArea1, "Starting get audit" + " " + new Date(), Color.BLACK);
                URL whatismyip = new URL("http://checkip.amazonaws.com");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                String ip = in.readLine(); //you get the IP as a String
                java.util.List<AuditRawDO> rawData = new AuditUtils().checkForAuditWithRetries(setup.getPrimaryServer(), policy,
                        new AuditFilterDO(),
                        StabilityAuditRawDo.getAllAuditParams(), 5, 5);
                AppendText.appendToPane(textArea1, "Get audit finish" + " " + new Date(), Color.BLACK);
                if (rawData != null && !rawData.isEmpty()) {
                    buildXML(rawData);
                    System.out.println("Build XML done");
                    if (new File(xmlPath + xmlFile).exists()) {
                        AppendText.appendToPane(textArea1, "SUCCESS", Color.GREEN);
                    } else {
                        AppendText.appendToPane(textArea1, "FAIL", Color.RED);
                    }
                } else {
                    AppendText.appendToPane(textArea1, "Audit verification fail" + " " + new Date(), Color.RED);
                }
            } catch (AutomationException | IOException e) {
                AppendText.appendToPane(textArea1, "Audit verification fail  with error message: " + e.getMessage() + " " + new Date(), Color.RED);
            }
        } else {
            AppendText.appendToPane(textArea1, "Get policy by name: " + policyName.getText() + " fail" + " " + new Date(), Color.RED);
        }
    }

    public void buildXML(java.util.List<AuditRawDO> rawData) throws ParserConfigurationException {
        java.util.List<eAuditPhaseTwo_parameters> eParams = StabilityAuditRawDo.getAllAuditParams();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("queries");
        doc.appendChild(rootElement);
        rawData = sortAudits(rawData);

        String rawQuery;
        for (AuditRawDO audit : rawData) {
            rawQuery = audit.getFieldValue(eAuditPhaseTwo_parameters.rawQuery);
            System.out.println("query: " + rawQuery + ", type: " + audit.getFieldValue(eAuditPhaseTwo_parameters.eventType));

            Element query = doc.createElement("query");

            switch (audit.getFieldValue(eAuditPhaseTwo_parameters.eventType)) {
                case "Login":
                    query.setAttribute("queryType", "connect");
                    query.setAttribute("query", "connect to the database: default");
                    query.setAttribute("database", "default");
                    break;
                case "Logout":
                    query.setAttribute("queryType", "disconnect");
                    query.setAttribute("query", "disconnect to the database: default");
                    query.setAttribute("database", "default");
                    break;
                default:
                    query.setAttribute("queryType", "statement");
                    query.setAttribute("query", audit.getFieldValue(eAuditPhaseTwo_parameters.rawQuery));
                    query.setAttribute("ignore", "false");
            }
            rootElement.appendChild(query);

            Element auditEventVerifications = doc.createElement("auditEventVerifications");
            auditEventVerifications.setAttribute("normalizedQuery", audit.getFieldValue(eAuditPhaseTwo_parameters.normalizedQuery));
            query.appendChild(auditEventVerifications);

            for (eAuditPhaseTwo_parameters e_par : eParams) {
                if(e_par == eAuditPhaseTwo_parameters.eventID)
                    continue;
                Element auditEventVerification = doc.createElement("auditEventVerification");
                auditEventVerification.setAttribute("searchColumn", "v_" + e_par.name());
                if(e_par == eAuditPhaseTwo_parameters.sourceIp || e_par == eAuditPhaseTwo_parameters.destinationIp
                        || e_par == eAuditPhaseTwo_parameters.sourceOfActivity) {
                    //this is a dynamic variable
                    auditEventVerification.setAttribute("searchValue", "${"+e_par.name()+"}");
                }
                else {
                    auditEventVerification.setAttribute("searchValue", audit.getFieldValue(e_par));
                }
                auditEventVerifications.appendChild(auditEventVerification);
            }
        }

        // write dom document to a file
        try (FileOutputStream output = new FileOutputStream(this.xmlPath + this.xmlFile)) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static java.util.List<AuditRawDO> sortAudits(java.util.List<AuditRawDO> rawData){
        Collections.sort(rawData, new Comparator<AuditRawDO>() {

            public int compare(AuditRawDO o1, AuditRawDO o2) {
                // compare two instance of `Score` and return `int` as result.
                return o2.getFieldValue(eAuditPhaseTwo_parameters.eventID).compareTo(o1.getFieldValue(eAuditPhaseTwo_parameters.eventID));
            }
        });
        Collections.reverse(rawData);
        return rawData;
    }


    private static void writeXml(Document doc, OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }


    private void create_json_audit() {
        String jsonPath = "";
        String jsonFile = "";

        ObjectMapper mapper = new ObjectMapper();
        AuditPolicyService ps = new AuditPolicyService();
        try {
            policy = ps.getPolicyByNameWithPredicates(setup.getPrimaryServer(), policyName.getText(), true);
//            policy = ps.getPolicyByName(setup.getPrimaryServer(), policyName.getText());
        } catch (AutomationException | NullPointerException e) {
            AppendText.appendToPane(textArea1, "Get policy by name fail with exception: " + e.getMessage() + " " + new Date(), Color.RED);
            return;
        }
        if (policy != null) {
            try {
                AppendText.appendToPane(textArea1, "Starting get audit" + " " + new Date(), Color.BLACK);
//                java.util.List<AuditRawDO> rawData = new AuditUtils().checkForAuditWithRetries(setup.getPrimaryServer(), policy,
//                        new AuditFilterDO().withAuditFilter(eAuditPhaseTwo_parameters.policy, policyName.getText()),
//                        StabilityAuditRawDo.getAllAuditParams(), 3, 5);
                URL whatismyip = new URL("http://checkip.amazonaws.com");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                String ip = in.readLine(); //you get the IP as a String
                System.out.println(ip);
                java.util.List<AuditRawDO> rawData = new AuditUtils().checkForAuditWithRetries(setup.getPrimaryServer(), policy,
                        new AuditFilterDO(),
                        StabilityAuditRawDo.getAllAuditParams(), 5, 5);
//                java.util.List<AuditRawDO> rawData = new AuditUtils().checkForFamAuditWithRetries(setup.getPrimaryServer(), policy,
//                        new AuditFilterDO(), StabilityAuditRawDo.getFamAuditParams(), 3, 5);
                AppendText.appendToPane(textArea1, "Get audit finish" + " " + new Date(), Color.BLACK);
                if (rawData != null && !rawData.isEmpty()) {
                    java.util.List<HashMap> jsonObj = new ArrayList<>();
                    java.util.List<eAuditPhaseTwo_parameters> eParams = StabilityAuditRawDo.getAllAuditParams();
//                    java.util.List<eAuditPhaseTwo_parameters> eParams = StabilityAuditRawDo.getFamAuditParams();
                    HashMap<String, String> obj;
                    for (AuditRawDO data : rawData) {
                        obj = new HashMap<>();
                        for (eAuditPhaseTwo_parameters e_par : eParams) {
                            obj.put(e_par.name(), data.getFieldValue(e_par));
                        }
                        jsonObj.add(obj);
                    }
                    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
                    writer.writeValue(new File(jsonPath + jsonFile), jsonObj);
                    if (new File(jsonPath + jsonFile).exists()) {
                        AppendText.appendToPane(textArea1, "SUCCESS", Color.GREEN);
                    } else {
                        AppendText.appendToPane(textArea1, "FAIL", Color.RED);
                    }
                } else {
                    AppendText.appendToPane(textArea1, "Audit verification fail" + " " + new Date(), Color.RED);
                }
            } catch (AutomationException | IOException e) {
                AppendText.appendToPane(textArea1, "Audit verification fail with error message: " + e.getMessage() + " " + new Date(), Color.RED);
            }
        } else {
            AppendText.appendToPane(textArea1, "Get policy by name: " + policyName.getText() + " fail" + " " + new Date(), Color.RED);
        }
//        ////
//        create_xml_from_json(jsonPath + jsonFile);
//        ///
    }
//    private void appendToPane(JTextPane textArea, String msg, Color c) {
//        StyleContext sc = StyleContext.getDefaultStyleContext();
//        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
//
//        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
//        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
//
//        int len = textArea.getDocument().getLength();
//        textArea.setCaretPosition(len);
//        textArea.setCharacterAttributes(aset, false);
//        textArea.replaceSelection(msg + "\n");
//    }

    @Override
    public void run() {
        runBtn.setEnabled(false);
        cleanBtn.setEnabled(false);
//        MonitoredEventReporter monitoredEventReporter = new MonitoredEventReporter();
//        monitoredEventReporter.register();
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        AppendText.appendToPane(textArea1, "1.5" + new Date(), Color.BLACK);
        initPassword(password.getText());
        SetupsService setupsService = new SetupsService();
        try {
            setup = setupsService.getSetupFromIPs(ip.getText(), new String[]{}, null);
        } catch (AutomationException | ArrayIndexOutOfBoundsException e) {
            AppendText.appendToPane(textArea1, "Connect to MX fail with exception: " + e.getMessage() + " " + new Date(), Color.RED);
            return;
        }
        //create_json_audit();
        try {
            create_xml_audit();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        setup = null;
        policy = null;
        System.gc();
        runBtn.setEnabled(true);
        cleanBtn.setEnabled(true);
        isRunning = false;
    }
}
