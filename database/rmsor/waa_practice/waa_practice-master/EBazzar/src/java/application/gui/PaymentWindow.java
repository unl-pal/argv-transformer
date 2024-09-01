package application.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This window provides textfields to enter
 * information for payment by credit card. If the user clicks
 * the Proceed With Checkout button, the Terms and Conditions window
 * appears before the Final Order is displayed.<p>
 * <em>Reading values from the cardTypeField combo box:</em>
 * Use the following syntax to read item selected:
 * <code>String cardTypeSelected = 
 * 		(String)cardTypeField.getSelectedItem();</code>
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
public class PaymentWindow extends JDialog implements ParentWindow {


	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;


	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Payment Method";
	/**
	 * @uml.property  name="pROCEED_BUTN"
	 */
	private final String PROCEED_BUTN = "Proceed With Checkout";
	/**
	 * @uml.property  name="bACK_TO_CART_BUTN"
	 */
	private final String BACK_TO_CART_BUTN = "Back To Cart";
	
	/**
	 * @uml.property  name="fIRST_NAME"
	 */
	private final String FIRST_NAME = "First Name";
	/**
	 * @uml.property  name="lAST_NAME"
	 */
	private final String LAST_NAME = "Last Name";
	/**
	 * @uml.property  name="nAME_ON_CARD"
	 */
	private final String NAME_ON_CARD = "Name on Card";
	/**
	 * @uml.property  name="cARD_NUMBER"
	 */
	private final String CARD_NUMBER = "Card Number";
	/**
	 * @uml.property  name="cARD_TYPE"
	 */
	private final String CARD_TYPE = "Card Type";
	/**
	 * @uml.property  name="eXPIRATION"
	 */
	private final String EXPIRATION = "Expiration Date";
	
	
	/**
	 * @uml.property  name="nameOnCardField"
	 * @uml.associationEnd  
	 */
	private JTextField nameOnCardField;
	/**
	 * @uml.property  name="cardNumberField"
	 * @uml.associationEnd  
	 */
	private JTextField cardNumberField;
	/**
	 * @uml.property  name="cardTypeField"
	 * @uml.associationEnd  
	 */
	private JComboBox cardTypeField;
	/**
	 * @uml.property  name="expirationField"
	 * @uml.associationEnd  
	 */
	private JTextField expirationField;
	

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
	
	class ProceedListener implements ActionListener {
	}
	class BackToCartListener implements ActionListener {
	}
	
	private static final long serialVersionUID = 3689071733583786041L;
	
}
