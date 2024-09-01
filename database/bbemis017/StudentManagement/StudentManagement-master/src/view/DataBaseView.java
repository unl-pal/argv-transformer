package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.AddRecordClicked;
import controller.Controller;
import controller.DeleteRecordClicked;
import controller.TableSwitchClicked;
import controller.UpdateRecordClicked;


@SuppressWarnings("serial")
public class DataBaseView extends JFrame{
	
	public static final int START_WIDTH=800, START_HEIGHT=700;
	
	private Controller control;
	public JTable table;
	
	private JPanel panel,tableSelection,editPanel;
	private JScrollPane scrollPane;
	private JButton studentButton, classButton, enrolledButton, addButton, updateButton, deleteButton;

}
