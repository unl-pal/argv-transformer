package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import carparksystem.controls.*;
import carparksystem.entities.*;

/** 
 * This is a boundary class providing a graphical interface for
 * administrator operations on staff record management.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class StaffRecordsUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton reportBtn;
	private JTable table;
	private JTextField campusCardTF, monthTF;
	
	//declare the information displayed in the table
	private String[] columnNames = {"Index",
            "Campus Card",
            "Enter Time",
            "Depart Time"};
	
	private Object[][] data = {};
}
