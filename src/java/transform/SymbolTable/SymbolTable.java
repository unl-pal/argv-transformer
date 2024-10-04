package transform.SymbolTable;

import java.util.HashMap;
/**
 * Class to represent a symbol table. A symbol table represents a scope in a program, 
 * containing some combination of classes, methods and variables. 
 * 
 * @author mariapaquin
 *
 */
public class SymbolTable {
	protected SymbolTable parent;
	protected String id;
	protected HashMap<String, SymbolSTE> table;
	
	/**
	 * Create a new SymbolTable.
	 * 
	 * @param parent Parent symbol table (i.e. the enclosing scope). 
	 */
	public SymbolTable(SymbolTable parent) {
		table = new HashMap<String, SymbolSTE>();
		this.parent = parent;
	}
	
	/**
	 * Put a symbol table element into the hashmap.
	 * 
	 * @param name Name of the symbol table element.
	 * @param sym Symbol table element.
	 */
	public void put(String name, SymbolSTE sym) {
		table.put(name, sym);
	}
	
	/**
	 * Get the parent symbol table. 
	 * 
	 * @return Parent SymbolTable.
	 */
	public SymbolTable getParent() {
		return parent;
	}
	
	/**
	 * Get the VarSTE representing the field variable in the closest 
	 * enclosing scope.
	 * 
	 * @param name Name of the variable.
	 * @return The VarSTE representing the field variable if it exists,
	 * null otherwise.
	 */
	public VarSTE getFieldVarSTE(String name) {
		VarSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof VarSTE && ((VarSTE) sym).isFieldVar()) {	
				found = (VarSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
	/**
	 * Get the VarSTE in the closest enclosing scope.
	 * 
	 * @param name Name of the variable.
	 * @return The VarSTE representing the variable if it exists, 
	 * null otherwise.
	 */
	public VarSTE getVarSTE(String name) {
		VarSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof VarSTE) {
				found = (VarSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
	/**
	 * Get the MethodSTE in the closest enclosing scope.
	 * 
	 * @param name Name of the method.
	 * @return The MethodSTE representing the method if it exists, 
	 * null otherwise.
	 */
	public MethodSTE getMethodSTE(String name) {
		MethodSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof MethodSTE) {
				found = (MethodSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
	/**
	 * Get the ClassSTE in the closest enclosing scope.
	 * 
	 * @param name Name of the class.
	 * @return The ClassSTE representing the class if it exists, 
	 * null otherwise.
	 */
	public ClassSTE getClassSTE(String name) {
		ClassSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof ClassSTE) {
				found = (ClassSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
	/**
	 * Get the symbol table.
	 * 
	 * @return symbol table.
	 */
	public HashMap<String, SymbolSTE> getTable(){
		return table;
	}

}
