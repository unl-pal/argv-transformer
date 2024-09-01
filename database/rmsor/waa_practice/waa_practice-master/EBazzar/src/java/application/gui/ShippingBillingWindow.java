package application.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This class presents a screen that displays
 * shipping and billing fields. The screen provides a Select Shipping
 * AddressImpl button that permits the user to pick, from another
 * screen, one of the customer's addresses, which is then loaded
 * automatically into the shipping fields on the current screen.
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
public class ShippingBillingWindow extends JDialog implements ParentWindow {
	

	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;
	
	//constants
	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Shipping And Billing Information";
	/**
	 * @uml.property  name="sHIP_LABEL"
	 */
	private final String SHIP_LABEL = "Shipping AddressImpl";
	/**
	 * @uml.property  name="bILL_LABEL"
	 */
	private final String BILL_LABEL = "Billing AddressImpl";
	/**
	 * @uml.property  name="sHIP_METHOD_LABEL"
	 */
	private final String SHIP_METHOD_LABEL = "Shipping Method";
	/**
	 * @uml.property  name="nAME"
	 */
	private final String NAME = "Name";
	/**
	 * @uml.property  name="aDDRESS_1"
	 */
	private final String ADDRESS_1 = "AddressImpl 1";
	/**
	 * @uml.property  name="aDDRESS_2"
	 */
	private final String ADDRESS_2 = "AddressImpl 2";
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
	private final String ZIP = "Zip";
	
	//button labels
	/**
	 * @uml.property  name="sELECT_SHIP_ADDR"
	 */
	private final String SELECT_SHIP_ADDR = "Select Shipping AddressImpl";
	/**
	 * @uml.property  name="pROCEED_WITH_CHECKOUT"
	 */
	private final String PROCEED_WITH_CHECKOUT = "Proceed With Checkout"; 
	/**
	 * @uml.property  name="bACK_TO_CART"
	 */
	private final String BACK_TO_CART = "Back To Cart";
	/**
	 * @uml.property  name="gROUND"
	 */
	private final String GROUND = "Pigeon-carrier Ground";
	/**
	 * @uml.property  name="aIR"
	 */
	private final String AIR = "Pigeon-carrier Air";
	/**
	 * @uml.property  name="oVERNIGHT"
	 */
	private final String OVERNIGHT = "Pigeon-carrier Overnight";
	/**
	 * @uml.property  name="cHECK_IF_SAME"
	 */
	private final String CHECK_IF_SAME = "Check if Billing AddressImpl is Same as Shipping";
	
	
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
	
	   //button row
	/**
	 * @uml.property  name="lower"
	 * @uml.associationEnd  
	 */
	JPanel lower;
	
	//upper divisions
	  //contains main label for screen
	/**
	 * @uml.property  name="upperTop"
	 * @uml.associationEnd  
	 */
	JPanel upperTop;
	  //contains address panels
	/**
	 * @uml.property  name="upperMiddle"
	 * @uml.associationEnd  
	 */
	JPanel upperMiddle;
	  //contains checkbox
	/**
	 * @uml.property  name="upperBottom"
	 * @uml.associationEnd  
	 */
	JPanel upperBottom;
	
	//upperMiddle divisions
	  //shipping address
	/**
	 * @uml.property  name="upperMiddleLeft"
	 * @uml.associationEnd  
	 */
	JPanel upperMiddleLeft;
	  //billing address
	/**
	 * @uml.property  name="upperMiddleRight"
	 * @uml.associationEnd  
	 */
	JPanel upperMiddleRight;
	
	//upperMiddleLeft divisions
	  //label for shipping
	/**
	 * @uml.property  name="upperMiddleLeftLabel"
	 * @uml.associationEnd  
	 */
	JPanel upperMiddleLeftLabel;
	  //address fields
	/**
	 * @uml.property  name="upperMiddleLeftFields"
	 * @uml.associationEnd  
	 */
	JPanel upperMiddleLeftFields;
	
	//upperMiddleRight divisions
	  //label for billing
	/**
	 * @uml.property  name="upperMiddleRightLabel"
	 * @uml.associationEnd  
	 */
	JPanel upperMiddleRightLabel;
	  //address fields
	/**
	 * @uml.property  name="upperMiddleRightFields"
	 * @uml.associationEnd  
	 */
	JPanel upperMiddleRightFields;
	
	//middle divisions
	   //shipping method label
	/**
	 * @uml.property  name="middleTop"
	 * @uml.associationEnd  
	 */
	JPanel middleTop;
	   //radio button panel
	/**
	 * @uml.property  name="middleBottom"
	 * @uml.associationEnd  
	 */
	JPanel middleBottom;
	
	//widgets
	
	
	/**
	 * @uml.property  name="shipNameField"
	 * @uml.associationEnd  
	 */
	private JTextField shipNameField;
	/**
	 * @uml.property  name="shipAddressField"
	 * @uml.associationEnd  
	 */
	private JTextField shipAddressField;
	
	/**
	 * @uml.property  name="shipCityField"
	 * @uml.associationEnd  
	 */
	private JTextField shipCityField;
	/**
	 * @uml.property  name="shipStateField"
	 * @uml.associationEnd  
	 */
	private JTextField shipStateField;
	/**
	 * @uml.property  name="shipZipField"
	 * @uml.associationEnd  
	 */
	private JTextField shipZipField;
	
	/**
	 * @uml.property  name="billNameField"
	 * @uml.associationEnd  
	 */
	private JTextField billNameField;
	/**
	 * @uml.property  name="billAddressField"
	 * @uml.associationEnd  
	 */
	private JTextField billAddressField;
	
	/**
	 * @uml.property  name="billCityField"
	 * @uml.associationEnd  
	 */
	private JTextField billCityField;
	/**
	 * @uml.property  name="billStateField"
	 * @uml.associationEnd  
	 */
	private JTextField billStateField;
	/**
	 * @uml.property  name="billZipField"
	 * @uml.associationEnd  
	 */
	private JTextField billZipField;	
	
	/**
	 * @uml.property  name="groundButton"
	 * @uml.associationEnd  
	 */
	private JRadioButton groundButton;
	/**
	 * @uml.property  name="airButton"
	 * @uml.associationEnd  
	 */
	private JRadioButton airButton;
	/**
	 * @uml.property  name="overnightButton"
	 * @uml.associationEnd  
	 */
	private JRadioButton overnightButton;
	
	/**
	 * @uml.property  name="addressesSameCheckbox"
	 * @uml.associationEnd  
	 */
	private JCheckBox addressesSameCheckbox;
	
	class SelectShipButtonListener implements ActionListener {
	}
	class ProceedCheckoutButtonListener implements ActionListener {
	}	
	
	class BackToCartButtonListener implements ActionListener {
	}
	
	private static final long serialVersionUID = 3256445819661072690L;
	
}
