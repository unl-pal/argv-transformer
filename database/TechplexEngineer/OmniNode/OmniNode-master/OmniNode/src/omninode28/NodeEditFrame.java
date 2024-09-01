/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omninode28;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author student
 */
public class NodeEditFrame extends JFrame implements ActionListener
{

    JFrame NEF;
    JTextField XField, YField;
    JTextField hField, wField;
    JTextField goals2c;
    JTextField goals4a;
    JTextField goals4c;
    JTextField balls2a;
    JTextField balls2c;
    JTextField spotOne2a;
    JTextField spotTwo2a;
    JTextField spotThree2a;
    JCheckBox cc1;
    JTextField canHang2b;
    JCheckBox tunnel2;
    JCheckBox bump2;
    JRadioButton guessed, visible, hidden;
    //JRadioButton offensive2b;
    JTextField control2a;
    JTextField speed2a;
    JTextField kick2a;
    JTextField note2;
    JTextField nameField;
    Node WN;
    NodePanel NP;
    JComboBox stateList;
}
