package application.gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.Box;
/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This is a factory class that provides utilities
 * for creating various screen elements in a standardized way. Also,
 * all screen element constants, like color names and screen sizes,
 * are stored here. 
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
public class GuiControl {
	private static GuiControl control;
	
	//location of files
	/** CURR_DIR is the current working directory, which is the directory of this project*/
	public static final String CURR_DIR = System.getProperty("user.dir");
	
	//if you do not use a "src" folder for source code, change the line below to this:
	//public static final String SPLASH_IMAGE = CURR_DIR+"\\src\\images\\logo.jpg";
	public static final String SPLASH_IMAGE = CURR_DIR+"\\images\\logo.jpg";
	
	public static int SCREEN_WIDTH = 640;
	public static int SCREEN_HEIGHT = 480;
	
	private static final int BOX_HEIGHT = 3;
	private static final int BOX_WIDTH = 3;
	
	public static final String EXIT_BUTN = "Exit E-Bazaar";
	
	public static final String ADD_NEW = "Add New";
	public static final String EDIT = "Edit";
	
	//pattern for formatting decimals so that they show only 2 decimal places
	public static final String DECIMAL_PATTERN = "0.00;-0.00";

	

	
	//colors
	//public static Color BROWN = new Color(0x9a7c46);
	//public static Color PALE_YELLOW = new Color(0xffface);
	//public static Color FAINT_YELLOW = new Color(0xffffe0);
	public static Color DARK_BLUE = Color.blue.darker();
	public static Color LIGHT_BLUE = new Color(0xf2ffff);
	public static Color DARK_GRAY = new Color(0xcccccc);
    public static Color APRICOT = new Color(0xfff2a9);
	
	public static Color MAIN_SCREEN_COLOR = LIGHT_BLUE;
	public static Color TABLE_BACKGROUND= LIGHT_BLUE;
	public static Color TABLE_PANE_BACKGROUND= LIGHT_BLUE;
	public static Color SCREEN_BACKGROUND = LIGHT_BLUE;
    public static Color QUANTITY_SCREEN_BGRND = APRICOT;
    public static Color QUANTITY_SCREEN_TEXT = DARK_BLUE;
	public static Color TABLE_HEADER_FOREGROUND = LIGHT_BLUE;
	public static Color TABLE_HEADER_BACKGROUND = DARK_BLUE;
	public static Color WINDOW_BORDER = DARK_BLUE;
	public static Color FILLER_COLOR = Color.white;	

}
