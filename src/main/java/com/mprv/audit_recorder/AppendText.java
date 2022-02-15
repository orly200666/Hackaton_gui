package com.mprv.audit_recorder;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

/**
 * Created by vladislavs on 12/21/2017.
 */
public class AppendText {

    public static void appendToPane(JTextPane textArea, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        textArea.setCharacterAttributes(aset, false);
        textArea.replaceSelection(msg + "\n");
    }
}
