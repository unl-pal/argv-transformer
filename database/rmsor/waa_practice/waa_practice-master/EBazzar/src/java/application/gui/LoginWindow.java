package application.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//import LoginControl;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This window provides textfields to enter
 * information for payment by credit card. If the user clicks
 * the Proceed With Checkout button, the Terms and Conditions window
 * appears before the Final Order is displayed.
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
public class LoginWindow extends JDialog implements ParentWindow {
	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;
	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Login";
	/**
	 * @uml.property  name="sUBMIT_BUTN"
	 */
	private final String SUBMIT_BUTN = "Submit";
	/**
	 * @uml.property  name="cANCEL_BUTN"
	 */
	private final String CANCEL_BUTN = "Cancel";
	
	/**
	 * @uml.property  name="cUST_ID"
	 */
	private final String CUST_ID = "CustomerSubsystemFacade Id";
	/**
	 * @uml.property  name="pASSWORD"
	 */
	private final String PASSWORD = "Password";

	
	/**
	 * @uml.property  name="custIdField"
	 * @uml.associationEnd  
	 */
	private JTextField custIdField;
	/**
	 * @uml.property  name="pwdField"
	 * @uml.associationEnd  
	 */
	private JPasswordField pwdField;
	

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
	
	private static final long serialVersionUID = 3258408422029144633L;
	
}
