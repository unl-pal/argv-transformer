package application.gui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author klevi, pcorazza 
 * @since Oct 22, 2004
 * <p>
 * Class Description: This table model permits manual control over columns -- lets
 * you have different column widths, for example. The DefaultTableModel
 * requires constant-width columns.
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
class CustomTableModel extends AbstractTableModel {
    
	//this is a List of Object arrays
    /**
	 * @uml.property  name="tableValues"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="[Ljava.lang.Object;"
	 */
    private List<Object[]> tableValues; 
    
    private static final long serialVersionUID = 3257846584573376055L;
	    

}
     