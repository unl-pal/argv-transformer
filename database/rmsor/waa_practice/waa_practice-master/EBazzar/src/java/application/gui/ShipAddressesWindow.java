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
import javax.swing.JWindow;


/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This class displays a list of all the
 * customer's addresses on record (on initial creation, just
 * one address is displayed, from fake data in DefaultData).
 * This class is invoked by ShippingBillingWindow as a means
 * to fill in the shipping address on its screen.  
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
public class ShipAddressesWindow extends JWindow implements ParentWindow {
	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;
	/**
	 * @uml.property  name="shipBillWindow"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ShippingBillingWindow shipBillWindow;
	/**
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
	CustomTableModel model;
	/**
	 * @uml.property  name="table"
	 * @uml.associationEnd  
	 */
	JTable table;
	/**
	 * @uml.property  name="tablePane"
	 * @uml.associationEnd  
	 */
	JScrollPane tablePane;
	
	//JPanels
	/**
	 * @uml.property  name="mainPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel mainPanel;
	/**
	 * @uml.property  name="upper"
	 * @uml.associationEnd  
	 */
	JPanel upper;
	/**
	 * @uml.property  name="middle"
	 * @uml.associationEnd  
	 */
	JPanel middle;
	/**
	 * @uml.property  name="lower"
	 * @uml.associationEnd  
	 */
	JPanel lower;
	
	//constants
	/**
	 * @uml.property  name="uSE_DEFAULT_DATA"
	 */
	private final boolean USE_DEFAULT_DATA = true;

    /**
	 * @uml.property  name="sTREET"
	 */
    private final String STREET = "Street";
    /**
	 * @uml.property  name="cITY"
	 */
    private final String CITY = "City";
    /**
	 * @uml.property  name="sTATE"
	 */
    private final String STATE = "State";
    /**
	 * @uml.property  name="zIP"
	 */
    private final String ZIP = "ZIP";
    /**
	 * @uml.property  name="mAIN_LABEL"
	 */
    private final String MAIN_LABEL = "Shipping Addresses";
    
    //button labels
    /**
	 * @uml.property  name="sELECT_BUTN"
	 */
    private final String SELECT_BUTN = "Select";
    /**
	 * @uml.property  name="cANCEL"
	 */
    private final String CANCEL = "Cancel";
    
    
    //table config
	/**
	 * @uml.property  name="dEFAULT_COLUMN_HEADERS" multiplicity="(0 -1)" dimension="1"
	 */
	private final String[] DEFAULT_COLUMN_HEADERS = {STREET,CITY,STATE, ZIP};
	/**
	 * @uml.property  name="tABLE_WIDTH"
	 */
	private final int TABLE_WIDTH = Math.round(0.75f*GuiControl.SCREEN_WIDTH);
    /**
	 * @uml.property  name="dEFAULT_TABLE_HEIGHT"
	 */
    private final int DEFAULT_TABLE_HEIGHT = Math.round(0.75f*GuiControl.SCREEN_HEIGHT);

    //these numbers specify relative widths of the columns -- they  must add up to 1
    /**
	 * @uml.property  name="cOL_WIDTH_PROPORTIONS" multiplicity="(0 -1)" dimension="1"
	 */
    private final float [] COL_WIDTH_PROPORTIONS =
    	{0.4f, 0.2f, 0.2f, 0.2f};

    	
    	
	/**
	 * @uml.property  name="eRROR_MESSAGE"
	 */
	final String ERROR_MESSAGE = "Please select a row.";
	/**
	 * @uml.property  name="eRROR"
	 */
	final String ERROR = "Error";
		

	class SelectListener implements ActionListener {
	}
	class CancelListener implements ActionListener {
	}
	

	private static final long serialVersionUID = 3256442521008944436L;

}
