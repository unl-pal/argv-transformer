package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import carparksystem.controls.*;
import carparksystem.entities.*;

/** 
 * This is a boundary class providing a graphical interface for
 * administrator operations on account management.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class AccountUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton regBtn, modifyBtn;
	private JTable table;
	
	private String[] columnNames = {"Index",
            "Name",
            "Campus Card",
            "E-mail",
            "Phone"};
	
	private Object[][] data = {};
}
