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
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: The TermsWindow class displays the terms
 * under which products are sold and shipped. The implementation
 * is a TextArea widget containing the terms information.
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
 *  <tr>
 * 		<td>Oct 26, 2004</td>
 *      <td>pcorazza</td>
 *      <td>Made the TextArea object non-modifiable.</td>
 * </tr>
 * </table>
 *
 */
public class TermsWindow extends JDialog implements ParentWindow {
	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;


	/**
	 * @uml.property  name="mAIN_LABEL"
	 */
	private final String MAIN_LABEL = "Terms and Conditions";
	/**
	 * @uml.property  name="pROCEED_BUTN"
	 */
	private final String PROCEED_BUTN = "Accept Terms And Conditions";
	/**
	 * @uml.property  name="eXIT_BUTN"
	 */
	private final String EXIT_BUTN = "Exit E-Bazaar";
	/**
	 * @uml.property  name="tERMS_MESSAGE"
	 */
	private final String TERMS_MESSAGE = "Any Items purchased from this site adhere to the terms and "+
										  "conditions depicted in this document. You will have to accecpt "+
										  "the Terms and Conditions depicted here inorder to purchase " +
										  "anything from this site.";
	
	
	
	/**
	 * @uml.property  name="termsText"
	 * @uml.associationEnd  
	 */
	private JTextArea termsText;
	
	

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

	
	private static final long serialVersionUID = 3258412811485853745L;

}
