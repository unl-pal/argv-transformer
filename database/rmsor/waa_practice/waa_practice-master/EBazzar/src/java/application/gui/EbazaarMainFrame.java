package application.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import application.BrowseSelectController;
/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This is the entry point into the E-Bazaar application.
 * It is implemented as a JFrame and provides a splash screen. Navigation
 * is by way of a menu bar. This class represents the starting point
 * for all use cases. <p>
 * Note: If splash image does not appear, modify the 
 * value stored in GuiControl.SPLASH_IMAGE so that it points
 * to logo.jpg.
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
public class EbazaarMainFrame extends javax.swing.JFrame {
	
	

	//ebazaar application title
	/**
	 * @uml.property  name="eBAZAAR_APP_NAME"
	 */
	private final String EBAZAAR_APP_NAME = "Ebazaar Online Shopping Application";
	
	//menu item names
	/**
	 * @uml.property  name="cUSTOMER"
	 */
	private final String CUSTOMER = "Customer";
	/**
	 * @uml.property  name="aDMINISTRATOR"
	 */
	private final String ADMINISTRATOR = "Administrator";
	/**
	 * @uml.property  name="oNLINE_PURCHASE"
	 */
	private final String ONLINE_PURCHASE = "Online Purchase";
	/**
	 * @uml.property  name="rEVIEW_ORDERS"
	 */
	private final String REVIEW_ORDERS = "Review Orders";
	/**
	 * @uml.property  name="eXIT"
	 */
	private final String EXIT = "Exit";
	/**
	 * @uml.property  name="rETRIEVE_CART"
	 */
	private final String RETRIEVE_CART = "Retrieve Saved Cart";
	/**
	 * @uml.property  name="mAINTAIN_CATALOGUE"
	 */
	private final String MAINTAIN_CATALOGUE = "Maintain Product Catalog";
	/**
	 * @uml.property  name="mAINTAIN_CAT_TYPES"
	 */
	private final String MAINTAIN_CAT_TYPES = "Maintain Catalog Types";
	
	/**
	 * @uml.property  name="mainPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JPanel mainPanel;
	/**
	 * @uml.property  name="menuBar"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	JMenuBar menuBar;
    /**
	 * @uml.property  name="menuCustomer"
	 * @uml.associationEnd  
	 */
    JMenu menuCustomer;
    /**
	 * @uml.property  name="menuAdministrator"
	 * @uml.associationEnd  
	 */
    JMenu menuAdministrator;
    /**
	 * @uml.property  name="menuItemPurchaseOnline"
	 * @uml.associationEnd  
	 */
    JMenuItem menuItemPurchaseOnline;
    /**
	 * @uml.property  name="menuItemMaintainProduct"
	 * @uml.associationEnd  
	 */
    JMenuItem menuItemMaintainProduct;
    /**
	 * @uml.property  name="menuItemMaintainCatalogTypes"
	 * @uml.associationEnd  
	 */
    JMenuItem menuItemMaintainCatalogTypes;
    /**
	 * @uml.property  name="menuItemExit"
	 * @uml.associationEnd  
	 */
    JMenuItem menuItemExit;
    /**
	 * @uml.property  name="menuItemRevOrders"
	 * @uml.associationEnd  
	 */
    JMenuItem menuItemRevOrders;
      
    //1)add the following line to the EbazaarMainFrame attribute declarations
    /**
	 * @uml.property  name="menuItemRetrieveSavedCart"
	 * @uml.associationEnd  
	 */
    JMenuItem menuItemRetrieveSavedCart;  

    
	class PurchaseOnlineActionListener implements ActionListener {
    }
    
    
    class MaintainProductActionListener implements ActionListener {
    }
    
    class SelectOrderActionListener implements ActionListener {
    }    
    
    class MaintainCatalogTypesActionListener implements ActionListener {
    }
    
    //3) add the following inner class to EbazaarMainFrame
	class RetrieveCartActionListener implements ActionListener {
	 }    
	 

	private static final long serialVersionUID = 3618418228695610676L;
   
    
}