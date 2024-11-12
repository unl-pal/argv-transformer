package myGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Casey on 7/21/2014.
 */
public class GUI extends JPanel implements ActionListener {

    private JLabel title;
    private JPanel titlePanel;
    private JPanel forDictionary;
    private JPanel forTextFile;
    private JPanel forRadioButtons;
    private JPanel forWriteFile;
    public static JTextArea output;
    private JTextField dictionary;
    private JTextField textFile;
    private JTextField writeFile;
    private JRadioButton printScreen;
    private JRadioButton printFile;
    private JButton runCheck = new JButton("Run SpellCheck");
    private String[] argsToPass = new String[3];

}
