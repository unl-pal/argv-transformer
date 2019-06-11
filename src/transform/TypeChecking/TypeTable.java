package transform.TypeChecking;

import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;
/**
 * Class to represent a type table. The type table maps each node 
 * in the program AST to a Type. 
 * 
 * @author mariapaquin
 *
 */
public class TypeTable {
	private HashMap<ASTNode, Type> typeTable;
	
	/**
	 * Create a new TypeTable.
	 */
	public TypeTable() {
		typeTable = new HashMap<ASTNode, Type>();
	}
	
	/**
	 * Get the type of a node in the AST.
	 * 
	 * @param node Node in the AST
	 * @return Type
	 */
	public Type getNodeType(ASTNode node) {
		return typeTable.get(node);
	}
	
	/**
	 * Set the type of a node in the AST.
	 * 
	 * @param node AST node
	 * @param type Type of node
	 */
	public void setNodeType(ASTNode node, Type type) {
		typeTable.put(node, type);
	}
}
