package application.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;

import application.BrowseSelectController;
import business.productSubsystem.BSstubProductSubsystemFacade;
import business.subsystemExternalInterfaces.Product;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This class provides detailed information
 * about a selected product. When the user clicks the Browse button
 * on the ProductListWindow screen, the product details for the selected
 * product appear on this screen. In this screen the user has the option
 * to add the product, whose detailed description is given here, to
 * the shopping cart (and if this option is chosen, the CartItemsWindow
 * is invoked).
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
public class ProductDetailsWindow extends JWindow implements ParentWindow {
	BrowseSelectController bsController;
	//////////////constants
	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Product Details";
	/**
	 * @uml.property  name="iTEM"
	 */
	private final String ITEM = "Item:";
	/**
	 * @uml.property  name="pRICE"
	 */
	private final String PRICE = "Price:";
	/**
	 * @uml.property  name="qUANTITY"
	 */
	private final String QUANTITY = "Quantity Available:";
	/**
	 * @uml.property  name="rEVIEW"
	 */
	private final String REVIEW = "Review:";
	
	/**
	 * @uml.property  name="aDD_TO_CART"
	 */
	private final String ADD_TO_CART = "Add To Cart";
	/**
	 * @uml.property  name="bACK_TO_CATALOG"
	 */
	private final String BACK_TO_CATALOG = "Back To Catalog";
	
	/**
	 * @uml.property  name="item"
	 */
	private String item;
	/**
	 * @uml.property  name="price"
	 */
	private double price;
	/**
	 * @uml.property  name="quantity"
	 */
	private int quantity;
	/**
	 * @uml.property  name="review"
	 */
	private String review;
	
	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;
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
	 * @uml.property  name="middleSubpanel"
	 * @uml.associationEnd  
	 */
	private JPanel middleSubpanel;
	/**
	 * @uml.property  name="lowerSubpanel"
	 * @uml.associationEnd  
	 */
	private JPanel lowerSubpanel;	

	class AddToCartListener implements ActionListener {
	}
	
	class BackToCatalogListener implements ActionListener {
	}
	
	private static final long serialVersionUID = 3904678297307919673L;
	
}
