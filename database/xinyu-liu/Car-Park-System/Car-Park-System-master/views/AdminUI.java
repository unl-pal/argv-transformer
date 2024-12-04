package carparksystem.views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import carparksystem.entities.Admin;

/** 
 * This is a boundary class providing a graphical interface for
 * administrator login.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class AdminUI  extends JPanel implements ActionListener  {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JTextField usernameTF;
	private JPasswordField pwdPF;
	private JButton loginBtn, clearBtn;
}
