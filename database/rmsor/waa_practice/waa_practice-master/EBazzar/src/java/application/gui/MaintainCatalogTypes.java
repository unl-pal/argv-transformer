package application.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This class presents a table of all catalogs
 * available in the database, and allows the user to add
 * edit or delete groups. Not all functionality for these buttons
 * is implemented on initial creation of this class.
 * <p>
 * <table border="1">
 * <tr>
 * 		<th colspan="3">Change Log</th>
 * </tr>
 * <tr>
 * 		<th>Date</th> <th>Author</th> <th>Change</th>
 * </tr>
 * <tr>
 * 		<td>Oct 22, 2004</td>
 *      <td>klevi, pcorazza</td>
 *      <td>New class file</td>
 * </tr>
 *<tr>
 * 		<td>19 jan 2005</td>
 *      <td>klevi</td>
 *      <td>modified readdata comment; changed static method, getCatalogtypes, to use class vs instance</td>
 * </tr>
 * </table>
 *
 */
public class MaintainCatalogTypes extends javax.swing.JWindow implements ParentWindow {


	/**
	 * Parent is used to return to main screen
	 * @uml.property  name="parent"
	 */
	private Window parent;

	
	/////////////constants
	
	//should be set to 'false' if data for table is obtained from a database
	//or some external file
	/**
	 * @uml.property  name="uSE_DEFAULT_DATA"
	 */
	private final boolean USE_DEFAULT_DATA = true;
	/**
	 * @uml.property  name="aDD"
	 */
	private final String ADD = "Add";
	/**
	 * @uml.property  name="eDIT"
	 */
	private final String EDIT = "Edit";
	/**
	 * @uml.property  name="dELETE"
	 */
	private final String DELETE = "Delete";
	/**
	 * @uml.property  name="bACK"
	 */
	private final String BACK = "Back";	
	
	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Maintain Category Types";
	
	//table configuration
	/**
	 * @uml.property  name="headers"
	 */
	private Properties headers;  
	
	/**
	 * @uml.property  name="tABLE_WIDTH"
	 */
	private final int TABLE_WIDTH = Math.round(0.75f*GuiControl.SCREEN_WIDTH);
    /**
	 * @uml.property  name="dEFAULT_TABLE_HEIGHT"
	 */
    private final int DEFAULT_TABLE_HEIGHT = Math.round(0.75f*GuiControl.SCREEN_HEIGHT);
    

	//JPanels
	/**
	 * @uml.property  name="mainPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JPanel mainPanel;
	/**
	 * @uml.property  name="upperSubpanel"
	 * @uml.associationEnd  
	 */
	private JPanel upperSubpanel;
	/**
	 * @uml.property  name="lowerSubpanel"
	 * @uml.associationEnd  
	 */
	private JPanel lowerSubpanel;
	
	//other widgets
	/**
	 * @uml.property  name="title"
	 */
	private String title;
	/**
	 * @uml.property  name="tableLabelText"
	 */
	private String tableLabelText;
	/**
	 * @uml.property  name="tableLabel"
	 * @uml.associationEnd  
	 */
	private JLabel tableLabel;
	/**
	 * @uml.property  name="tablePane"
	 * @uml.associationEnd  
	 */
	private JScrollPane tablePane;
	/**
	 * @uml.property  name="table"
	 * @uml.associationEnd  
	 */
	private JTable table;
	/**
	 * @uml.property  name="dEFAULT_COLUMN_HEADERS" multiplicity="(0 -1)" dimension="1"
	 */
	private final String[] DEFAULT_COLUMN_HEADERS = {"Name of Category Group"};
    //these numbers specify relative widths of the columns -- they  must add up to 1
    /**
	 * @uml.property  name="cOL_WIDTH_PROPORTIONS" multiplicity="(0 -1)" dimension="1"
	 */
    private final float [] COL_WIDTH_PROPORTIONS =
    	{1.0f};	
	
    /**
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
    private CustomTableModel model;
 

 
	/**
	 * @uml.property  name="eRROR_MESSAGE"
	 */
	final String ERROR_MESSAGE = "Please select a row.";
	/**
	 * @uml.property  name="eRROR"
	 */
	final String ERROR = "Error";
		
	class AddButtonListener implements ActionListener {        	
 
	
	}
	class EditButtonListener implements ActionListener {
	}
	class DeleteButtonListener implements ActionListener {
	}	
	
	
	class BackButtonListener implements ActionListener {
	}	
	
	private static final long serialVersionUID = 3258410629793592632L;


}