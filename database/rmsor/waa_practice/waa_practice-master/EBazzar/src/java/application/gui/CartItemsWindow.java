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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;

import application.BrowseSelectController;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This window represents a user's shopping cart. 
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
 * 		<td>Jan 19, 2005</td>
 *      <td>klevi</td>
 *      <td>Modified comments for readdata and updatemodel</td>
 * </tr>
 * </table>
 *
 */
public class CartItemsWindow extends JWindow implements ParentWindow {
	BrowseSelectController bsController;
	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;
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
	private final boolean USE_DEFAULT_DATA = false;

    /**
	 * @uml.property  name="iTEM"
	 */
    private final String ITEM = "Item";
    /**
	 * @uml.property  name="qUANTITY"
	 */
    private final String QUANTITY = "Quantity";
    /**
	 * @uml.property  name="uNIT_PRICE"
	 */
    private final String UNIT_PRICE = "Unit Price";
    /**
	 * @uml.property  name="tOTAL"
	 */
    private final String TOTAL = "Total Price";
    /**
	 * @uml.property  name="mAIN_LABEL"
	 */
    private final String MAIN_LABEL = "Cart Items";
    
    //button labels
    /**
	 * @uml.property  name="pROCEED_BUTN"
	 */
    private final String PROCEED_BUTN = "Proceed To Checkout";
    /**
	 * @uml.property  name="cONTINUE"
	 */
    private final String CONTINUE = "Continue Shopping";
    /**
	 * @uml.property  name="sAVE_CART"
	 */
    private final String SAVE_CART = "Save Cart";
    /**
	 * @uml.property  name="eXIT_BUTN"
	 */
    private final String EXIT_BUTN = "Exit E-Bazaar";
    
    //table data and config
	/**
	 * @uml.property  name="dEFAULT_COLUMN_HEADERS" multiplicity="(0 -1)" dimension="1"
	 */
	private final String[] DEFAULT_COLUMN_HEADERS = {ITEM,QUANTITY,UNIT_PRICE,TOTAL};
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

    	
    	
	class ProceedListener implements ActionListener {
	}
	class ContinueListener implements ActionListener {
	}
	class SaveCartListener implements ActionListener {
	}	
	
	private static final long serialVersionUID = 1L;	
}
