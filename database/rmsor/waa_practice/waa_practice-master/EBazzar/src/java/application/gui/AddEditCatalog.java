package application.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/** 
 * 
 * @author klevi, pcorazza 
 * @since 10/20/04
 * <p>
 * Class Description: This class is responsible for building
 * the window for adding or editing a catalog group. 
 * <p>
 * <table border="1">
 * <tr>
 * 		<th colspan="3">Change Log</th>
 * </tr>
 * <tr>
 * 		<th>Date</th> <th>Author</th> <th>Change</th>
 * </tr>
 * <tr>
 * 		<td>10/20/04</td>
 *      <td>klevi, pcorazza</td>
 *      <td>New class file</td>
 * </tr>
 * <tr>
 * 		<td>1/19/04</td>
 *      <td>klevi</td>
 *      <td>renamed class--previously AddEditCatalogGroup</td>
 * </tr>
 * </table>
 *
 */
public class AddEditCatalog extends JDialog implements ParentWindow {
	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;


	/**
	 * final value of label will be set in the constructor
	 * @uml.property  name="mainLabel"
	 */
	private String mainLabel = " CatalogImpl Type";
	/**
	 * @uml.property  name="sAVE_BUTN"
	 */
	private final String SAVE_BUTN = "Save";
	/**
	 * @uml.property  name="bACK_BUTN"
	 */
	private final String BACK_BUTN = "Close";
	

	
	/**
	 * @uml.property  name="pRODUCT_NAME"
	 */
	private final String PRODUCT_NAME = "ProductImpl Name";
	/**
	 * @uml.property  name="productNameField"
	 * @uml.associationEnd  
	 */
	private JTextField productNameField;

	/**
	 * catalogGroup have value "Books", "Clothes" etc
	 * @uml.property  name="catalogGroup"
	 */
	private String catalogGroup;
	
	/**
	 * value is "Add New" or "Edit"
	 * @uml.property  name="addOrEdit"
	 */
	private String addOrEdit = GuiControl.ADD_NEW;
	

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
	
	class SaveListener implements ActionListener {
	}
	/**
	 * Returns the user to the previous screen
	 */
	class BackListener implements ActionListener {
	}
	
	private static final long serialVersionUID = 1L;	
}
