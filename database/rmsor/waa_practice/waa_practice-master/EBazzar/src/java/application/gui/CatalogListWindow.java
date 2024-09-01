package application.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import business.productSubsystem.BSstubProductSubsystemFacade;

import application.BrowseSelectController;
import business.productSubsystem.MPTestProductSubsystemFacade;


/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This screen presents the list of all E-Bazaar
 * catalogs. As of creation date, there were just two catalogs in the 
 * default data: Books
 * and Clothes. Clicking the Browse button when one of the catalogs
 * has been selected invokes an instance
 * of ProductListWindow, displaying the available items for the selected
 * catalog.
 * Students:  See the readdata method for where data is put into the table.
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
 * 		<td>jan 19 2005</td>
 *      <td>klevi</td>
 *      <td>modified class and readdata comments</td>
 * </tr>
 * </table>
 *
 */
public class CatalogListWindow extends javax.swing.JWindow implements ParentWindow {
	
	/* windows talk to the controllers, not vice versa */
	//private BrowseSelectController bsController = new BrowseSelectController(new BSstubProductSubsystemFacade());
        private BrowseSelectController bsController = new BrowseSelectController(new MPTestProductSubsystemFacade());

        
	/**
	 * Parent is used to return to main screen
	 * @uml.property  name="parent"
	 */
	private Window parent;

	//////////////constants
	
	//should be set to 'false' if data for table is obtained from a database
	//or some external file
	/**
	 * @uml.property  name="uSE_DEFAULT_DATA"
	 */
	private final boolean USE_DEFAULT_DATA = false;
	

	
	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Browse CatalogImpl";
	/**
	 * @uml.property  name="bROWSE"
	 */
	private final String BROWSE = "Browse";
	/**
	 * @uml.property  name="bACK_TO_MAIN"
	 */
	private final String BACK_TO_MAIN = "Back To Main";
	/**
	 * @uml.property  name="tABLE_WIDTH"
	 */
	private final int TABLE_WIDTH = Math.round(0.75f*GuiControl.SCREEN_WIDTH);
    /**
	 * @uml.property  name="dEFAULT_TABLE_HEIGHT"
	 */
    private final int DEFAULT_TABLE_HEIGHT = Math.round(0.75f*GuiControl.SCREEN_HEIGHT);
	/**
	 * @uml.property  name="dEFAULT_COLUMN_HEADERS" multiplicity="(0 -1)" dimension="1"
	 */
	private final String[] DEFAULT_COLUMN_HEADERS = {"Available Catalogs"};
    //these numbers specify relative widths of the columns -- they  must add up to 1
    /**
	 * @uml.property  name="cOL_WIDTH_PROPORTIONS" multiplicity="(0 -1)" dimension="1"
	 */
    private final float [] COL_WIDTH_PROPORTIONS =
    	{1.0f};

	//JPanels
	/**
	 * @uml.property  name="mainPanel"
	 * @uml.associationEnd  
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
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
	private CustomTableModel model;
	 
    public static CatalogListWindow instance;
    
    class BrowseButtonListener implements ActionListener {
	}
	
	class BackToMainButtonListener implements ActionListener {
	}
	
	private static final long serialVersionUID = 3258411720664953398L;
	
}