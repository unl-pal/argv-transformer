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
 * Class Description: This window represents the user's final order.
 * It consists of a table that provide detailed information about
 * each of the user's selected products.
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
 * </table>
 *
 */
public class FinalOrderWindow extends JWindow implements ParentWindow {


	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;


	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Final Order";
	/**
	 * @uml.property  name="sUBMIT_BUTN"
	 */
	private final String SUBMIT_BUTN = "Submit Order";
	/**
	 * @uml.property  name="cANCEL_BUTN"
	 */
	private final String CANCEL_BUTN = "Cancel";
	
	/**
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
	private CustomTableModel model;
	/**
	 * @uml.property  name="table"
	 * @uml.associationEnd  
	 */
	private JTable table;
	/**
	 * @uml.property  name="tablePane"
	 * @uml.associationEnd  
	 */
	private JScrollPane tablePane;
	
	
	//table
	/**
	 * @uml.property  name="uSE_DEFAULT_DATA"
	 */
	private final boolean USE_DEFAULT_DATA = true;
		
	
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
	
	class SubmitListener implements ActionListener {
	}
	class CancelListener implements ActionListener {
	}
	
	private static final long serialVersionUID = 3906648600670122544L;
	
}
