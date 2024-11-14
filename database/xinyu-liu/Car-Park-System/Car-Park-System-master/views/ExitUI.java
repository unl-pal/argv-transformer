package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import carparksystem.controls.*;

/** 
 * This is a boundary class providing a graphical interface for
 * customer operations at the Exit.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class ExitUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JButton staffBtn, publicBtn, emergencyBtn;
	private JTextField inputTF;
}
