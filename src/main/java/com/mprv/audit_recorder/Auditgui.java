package com.mprv.audit_recorder;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;

public class Auditgui extends JFrame{
    private JPanel mainPanel;
    private JPanel textPanel;
    private JPanel consolePanel;
    private JPanel btnPanel;

    private JButton runBtn;

    private JTextField ip;
    private JTextField path;
    private JTextField password;
    private JTextField policyName;
    private JTextField fileName;

    private JLabel ipLabel;
    private JLabel pathLabel;
    private JLabel passwordLabel;
    private JLabel policyNameLabel;
    private JLabel fileNameLabel;

    private JTextPane textArea1;
    private JScrollPane scroll;

    //public JTextField(String text,int columns)

    public Auditgui(){

        mainPanel = new JPanel();
        setContentPane(mainPanel);

        textPanel = new JPanel();

        ipLabel = new JLabel();
        ipLabel.setText("MX IP");
        passwordLabel = new JLabel();
        passwordLabel.setText("MX Password");
        policyNameLabel = new JLabel();
        policyNameLabel.setText("Policy Name");
        fileNameLabel = new JLabel();
        fileNameLabel.setText("JSON File Name");
        pathLabel = new JLabel();
        pathLabel.setText("JSON File Path");

        ip = new JTextField(10);
        path = new JTextField(10);
        password = new JTextField(10);
        policyName = new JTextField(10);
        fileName = new JTextField(10);

        textPanel.add(ipLabel);
        textPanel.add(ip);
        textPanel.add(passwordLabel);
        textPanel.add(password);
        textPanel.add(policyNameLabel);
        textPanel.add(policyName);
        textPanel.add(fileNameLabel);
        textPanel.add(fileName);
        textPanel.add(pathLabel);
        textPanel.add(path);

        consolePanel = new JPanel();
        scroll = new JScrollPane();
        textArea1 = new JTextPane();
        scroll.setViewportView(textArea1);
        consolePanel.add(scroll);

        btnPanel = new JPanel();
        runBtn = new JButton();
        runBtn.setPreferredSize(new Dimension(40, 40));
        btnPanel.add(runBtn);

        mainPanel.add(textPanel);
        mainPanel.add(consolePanel);
        mainPanel.add(btnPanel);

        //mainPanel.setSize(100);
        //textPanel.setLayout(new FlowLayout());
        //textPanel.setLayout(new DefaultMenuLayout(100));
        //textPanel.setLayout(new DefaultMenuLayout( new Container(), -1));
        textPanel.setSize(400, 300);
        textPanel.setAlignmentX(5);

    }

//    public static void convertJsonTOXML(String JsonPath, String xmlPath)
//    {
//
//    }
    public static void main(String[] args) {
        System.out.println("lala");
        Auditgui auditGui = new Auditgui();

        auditGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auditGui.setSize(700, 500);
        auditGui.setTitle("Audit Recorder");
//
//        //tryCleanAudit();


        //convertJsonTOXML();
        auditGui.setVisible(true);



    }
}
