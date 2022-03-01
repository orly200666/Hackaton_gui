package com.mprv.audit_recorder;

import com.mprv.automation.machines.Setup;
import com.mprv.automation.services.SecureSphereServices.policies.AuditPolicyDO;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Date;

/**
 * Created by vladislavs on 11/27/2017.
 */
public class AuditRecorder {

    private JButton runBtn;
    private JTextField ip;
    private JTextField path;
    private JTextField password;
    private JTextField policyName;
    private JPanel textPanell;

    private JPanel panel1;
    private JTextField fileName;
    //    private JTextArea textArea1;
    private JPanel consolePunel;
    private JTextPane textArea1;
    private JButton cleanDataBtn;
    private JRadioButton createJSONFileRadioButton;
    private JRadioButton createXMLFileRadioButton;

    JFrame jFrame;
    static Setup setup = null;
    static AuditPolicyDO policy = null;
    String file;
    String filePath;
    ActionListener action;
    boolean ignoreEvents = false;

    boolean createJsonFile;
    boolean createXMLFile;


    public AuditRecorder() {
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(null);
//                cleanDataBtn.setEnabled(false);
                if (checkIfFieldIsClear()) {
                    AppendText.appendToPane(textArea1, "Program is running please wait..." + " " + new Date(), Color.BLACK);
                    if (path.getText().endsWith("/")) {
                        filePath = path.getText();
                    } else {
                        filePath = path.getText() + "/";
                    }
                    if (createXMLFileRadioButton.isSelected()){
                        file = fileName.getText() + ".xml";
                    }
                    else{
                        //create JSON file
                        //if(createJSONFileRadioButton.isSelected())
                        file = fileName.getText() + ".json";
                    }

                    if (new File(filePath).exists()) {
                        if (!new File(filePath + file).exists()) {
                            Infra infra = new Infra(textArea1, policyName, password, ip, filePath, file, runBtn, cleanDataBtn);
                            Thread thread = new Thread(infra);
                            thread.setDaemon(true);
                            thread.start();
                        } else {
                            AppendText.appendToPane(textArea1, "File : " + file + " is currently exist in given path " + filePath + " " + new Date(), Color.RED);
                        }
                    } else {
                        AppendText.appendToPane(textArea1, "Path: " + filePath + " is not valid.", Color.RED);
                    }
                }
//                cleanDataBtn.setEnabled(true);
            }
        });

        cleanDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(null);
//                runBtn.setEnabled(false);
                AppendText.appendToPane(textArea1, "Program is running please wait..." + " " + new Date(), Color.BLACK);

                if (checkIfFieldIsClearForCleaAudit()) {
                    CleanAudit cleanAudit = new CleanAudit(ip, textArea1, policyName, password, cleanDataBtn, runBtn);
                    Thread thread = new Thread(cleanAudit);
                    thread.setDaemon(true);
                    thread.start();
                }
//                runBtn.setEnabled(true);
            }
        });
        createXMLFileRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(null);
                AppendText.appendToPane(textArea1, "You choose to create XML file \nPlease fill in the text box. ", Color.BLACK);
                createXMLFile = true;
                createJsonFile = false;
                if(createJSONFileRadioButton.isSelected())
                {
                    createJSONFileRadioButton.setSelected(false);
                }
            }
        });
        createJSONFileRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(null);
                AppendText.appendToPane(textArea1, "You choose to create JSON file \nPlease fill in the text box. ", Color.BLACK);
                createXMLFile = false;
                createJsonFile = true;
                if(createXMLFileRadioButton.isSelected())
                {
                    createXMLFileRadioButton.setSelected(false);
                }
            }
        });
    }


    public boolean checkIfFieldIsClear() {
        if (ip.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field MX IP has empty value" + " " + new Date(), Color.RED);
            return false;
        } else if (password.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field Password has empty value" + " " + new Date(), Color.RED);
            return false;
        } else if (policyName.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field Policy Name has empty value" + " " + new Date(), Color.RED);
            return false;
        } else if (fileName.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field File Name has empty value" + " " + new Date(), Color.RED);
            return false;
        } else if (path.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field Path has empty value" + " " + new Date(), Color.RED);
            return false;
        }
        return true;
    }

    public boolean checkIfFieldIsClearForCleaAudit() {
        if (ip.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field MX IP has empty value" + " " + new Date(), Color.RED);
            return false;
        } else if (password.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field Password has empty value" + " " + new Date(), Color.RED);
            return false;
        } else if (policyName.getText().isEmpty()) {
            AppendText.appendToPane(textArea1, "Field Policy Name has empty value" + " " + new Date(), Color.RED);
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws ParserConfigurationException {
        AuditRecorder main = new AuditRecorder();
        main.Go();
    }

    public void Go() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame jFrame = new JFrame("AuditRecorder");
        jFrame.setContentPane(panel1);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setSize(700, 500);
        action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(null);
                if (checkIfFieldIsClear()) {
                    AppendText.appendToPane(textArea1,"Program is running please wait...", Color.BLACK);
                    if (path.getText().endsWith("/")) {
                        filePath = path.getText();
                    } else {
                        filePath = path.getText() + "/";
                    }
                    if (createXMLFileRadioButton.isSelected()){
                        file = fileName.getText() + ".xml";
                    }
                    else{
                        //create JSON file
                        //if(createJSONFileRadioButton.isSelected())
                        file = fileName.getText() + ".json";
                    }
                    if (new File(filePath).exists()) {
                        if (!new File(filePath + file).exists()) {
                                Infra infra = new Infra(textArea1, policyName, password, ip, filePath, file, runBtn, cleanDataBtn);
                            Thread thread = new Thread(infra);
                            thread.setDaemon(true);
                            //thread.start();
                        } else {
                            AppendText.appendToPane(textArea1,"File : " + file + " is currently exist in given path " + filePath, Color.RED);
                        }
                    } else {
                        AppendText.appendToPane(textArea1,"Path: " + filePath + " is not valid.", Color.RED);
                    }
                }
            }
        };
        runBtn.addActionListener(action);
        jFrame.setVisible(true);


    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        textPanell = new JPanel();
        textPanell.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(10, 1, new Insets(0, 10, 0, 0), -1, -1));
        panel1.add(textPanell, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ip = new JTextField();
        ip.setText("");
        textPanell.add(ip, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        path = new JTextField();
        path.setText("");
        textPanell.add(path, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        password = new JTextField();
        password.setText("");
        textPanell.add(password, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        policyName = new JTextField();
        policyName.setText("");
        textPanell.add(policyName, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        fileName = new JTextField();
        fileName.setText("");
        textPanell.add(fileName, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("MX Password");
        textPanell.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Policy Name");
        textPanell.add(label2, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("XML File Name");
        textPanell.add(label3, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("XML File Path");
        textPanell.add(label4, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("MX IP");
        textPanell.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        runBtn = new JButton();
        runBtn.setText("Run");
        panel2.add(runBtn, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cleanDataBtn = new JButton();
        cleanDataBtn.setText("Clean audit");
        panel2.add(cleanDataBtn, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        consolePunel = new JPanel();
        consolePunel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 10), -1, -1));
        panel1.add(consolePunel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        consolePunel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textArea1 = new JTextPane();
        scrollPane1.setViewportView(textArea1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    //    private void appendToPane(String msg, Color c) {
//        StyleContext sc = StyleContext.getDefaultStyleContext();
//        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
//
//        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
//        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
//
//        int len = textArea1.getDocument().getLength();
//        textArea1.setCaretPosition(len);
//        textArea1.setCharacterAttributes(aset, false);
//        textArea1.replaceSelection(msg + "\n");
//    }

}
