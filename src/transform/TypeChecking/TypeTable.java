package transform.TypeChecking;

import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;

public class TypeTable {
	private HashMap<ASTNode, Type> typeTable;
	
	public TypeTable() {
		typeTable = new HashMap<ASTNode, Type>();
	}
	
	public Type getNodeType(ASTNode node) {
		return typeTable.get(node);
	}
	
	public void setNodeType(ASTNode node, Type type) {
		typeTable.put(node, type);
	}
}
