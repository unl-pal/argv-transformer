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
import javax.swing.JTextField;

import business.subsystemExternalInterfaces.Product;

import application.BrowseSelectController;
public class QuantityWindow extends JDialog {
	BrowseSelectController bsController;
	String productName;
	/**
	 * @uml.property  name="parent"
	 */
	private Window parent;


    /**
	 * @uml.property  name="mAIN_LABEL"
	 */
    private final String MAIN_LABEL = "Quantity Desired";
    /**
	 * @uml.property  name="oK_BUTN"
	 */
    private final String OK_BUTN = "OK";
    /**
	 * @uml.property  name="cANCEL_BUTN"
	 */
    private final String CANCEL_BUTN = "Cancel";
    
    
    /**
	 * @uml.property  name="quantityField"
	 * @uml.associationEnd  
	 */
    private JTextField quantityField;
    
    

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
    class OkListener implements ActionListener {
    }
    class CancelListener implements ActionListener {
    }      
    private static final long serialVersionUID = 3618135641289078841L;



}
