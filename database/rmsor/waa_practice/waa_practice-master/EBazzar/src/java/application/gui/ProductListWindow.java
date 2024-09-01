package application.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import application.BrowseSelectController;


/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This window displays all known products belonging
 * to the selected catalog group. The catalog group is passed into
 * the constructor as  a parameter. In the final version of the
 * application, the products displayed should be all products in
 * the database having category group equal to this parameter. 
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
 * <tr>
 * 		<td>Jan 19, 2005</td>
 *      <td>klevi</td>
 *      <td>modifed the readdata comments</td>
 * </tr>
 * </table>
 *
 */
public class ProductListWindow extends javax.swing.JWindow implements ParentWindow {
	/* windows talk to the controllers, not vice versa 
	 *  - should get passed in by whoever opens the window, e.g., CatalogListWindow
	 */
	private BrowseSelectController bsController;
	

	//constants
	/**
	 * @uml.property  name="aVAIL_BOOKS"
	 */
	private final String AVAIL_BOOKS = "List of Available Books";
 	/**
	 * @uml.property  name="aVAIL_CLOTHING"
	 */
 	private final String AVAIL_CLOTHING = "List of Available Clothing";
    /**
	 * @uml.property  name="cOL_WIDTH_PROPORTIONS" multiplicity="(0 -1)" dimension="1"
	 */
    private final float [] COL_WIDTH_PROPORTIONS =	{1.0f};
 	
 	
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
	private final boolean USE_DEFAULT_DATA = false;
	/**
	 * @uml.property  name="sELECT"
	 */
	private final String SELECT = "Select";
	/**
	 * @uml.property  name="bACK"
	 */
	private final String BACK = "Back";
	
	
	//table configuration
	/**
	 * @uml.property  name="headers"
	 */
	private Properties headers; 
	/**
	 * @uml.property  name="header" multiplicity="(0 -1)" dimension="1"
	 */
	private String[] header;
	
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
	/**
	 * @uml.property  name="labelPanel"
	 * @uml.associationEnd  
	 */
	private JPanel labelPanel;
	
	//other widgets
	/**
	 * @uml.property  name="title"
	 */
	private String title;
	/**
	 * @uml.property  name="mainLabelText"
	 */
	private String mainLabelText;
	/**
	 * @uml.property  name="mainLabel"
	 * @uml.associationEnd  readOnly="true"
	 */
	private JLabel mainLabel;
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
	 * @uml.property  name="catalogType"
	 */
	private String catalogType;
    /**
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
    private CustomTableModel model;
 
 	class SelectButtonListener implements ActionListener {
	}
	
	class BackButtonListener implements ActionListener {
	}		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3834024779601818169L;

}