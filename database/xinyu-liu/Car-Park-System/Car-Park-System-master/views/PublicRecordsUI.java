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
 * administrator operations on public record management.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class PublicRecordsUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	//declare the information displayed in the table
	private String[] columnNames = {"Index",
            "Public number",
            "Enter Time",
            "Depart Time",
            "Payment"};
	
	private Object[][] data = {};
}
