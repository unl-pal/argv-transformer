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
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * Class Description: This class displays all available products
 * for a particular catalog group. When a catalog group is selected,
 * the table is updated to display the products in this group. 
 * The screen provides Add, Edit and Delete buttons for modifying
 * the choices of products.
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
 *      <td>modifed the readdata comments</td>
 * </tr>
 * </table>
 *
 */
public class MaintainProductCatalog extends JWindow implements ParentWindow {
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
	 * @uml.property  name="comboPanel"
	 * @uml.associationEnd  
	 */
	JPanel comboPanel;
	/**
	 * @uml.property  name="lower"
	 * @uml.associationEnd  
	 */
	JPanel lower;
	
	//widgets
	/**
	 * @uml.property  name="catalogTypeCombo"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	JComboBox catalogTypeCombo;	
	
	//catalog type (books, clothes, etc); set default to Books
	/**
	 * @uml.property  name="catalogType"
	 */
	String catalogType = DefaultData.BOOKS;
	
	//constants
	/**
	 * @uml.property  name="uSE_DEFAULT_DATA"
	 */
	private final boolean USE_DEFAULT_DATA = true;

    /**
	 * @uml.property  name="nAME"
	 */
    private final String NAME = "Name";
    /**
	 * @uml.property  name="pRICE"
	 */
    private final String PRICE = "Unit Price";
    /**
	 * @uml.property  name="mFG_DATE"
	 */
    private final String MFG_DATE = "Mfg. Date";
    /**
	 * @uml.property  name="qUANTITIES"
	 */
    private final String QUANTITIES = "Quantities";
    
    /**
	 * @uml.property  name="mAIN_LABEL"
	 */
    private final String MAIN_LABEL = "Maintain ProductImpl CatalogImpl";
    
    //widget labels
    /**
	 * @uml.property  name="cAT_GROUPS"
	 */
    private final String CAT_GROUPS = "CatalogImpl Groups";
    /**
	 * @uml.property  name="aDD_BUTN"
	 */
    private final String ADD_BUTN = "Add";
    /**
	 * @uml.property  name="eDIT_BUTN"
	 */
    private final String EDIT_BUTN = "Edit";
    /**
	 * @uml.property  name="dELETE_BUTN"
	 */
    private final String DELETE_BUTN = "Delete";
    /**
	 * @uml.property  name="sEARCH_BUTN"
	 */
    private final String SEARCH_BUTN = "Search";
    /**
	 * @uml.property  name="bACK_TO_MAIN"
	 */
    private final String BACK_TO_MAIN = "Back to Main";
    
    
    //table config
	/**
	 * @uml.property  name="dEFAULT_COLUMN_HEADERS" multiplicity="(0 -1)" dimension="1"
	 */
	private final String[] DEFAULT_COLUMN_HEADERS = {NAME,PRICE,MFG_DATE,QUANTITIES};
	/**
	 * @uml.property  name="tABLE_WIDTH"
	 */
	private final int TABLE_WIDTH = GuiControl.SCREEN_WIDTH;
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

    	
    	
	/**
	 * @uml.property  name="eRROR_MESSAGE"
	 */
	final String ERROR_MESSAGE = "Please select a row.";
	/**
	 * @uml.property  name="eRROR"
	 */
	final String ERROR = "Error";
	
	class ComboBoxListener implements ActionListener {
	}
	class AddButtonListener implements ActionListener {        	
 
	}
	
	
	class EditButtonListener implements ActionListener {
	}
	class DeleteButtonListener implements ActionListener {
	}	
	
	class SearchButtonListener implements ActionListener {
	}
	class BackToMainButtonListener implements ActionListener {
	}			
	
	private static final long serialVersionUID = 3257569511937880631L;
	
}
