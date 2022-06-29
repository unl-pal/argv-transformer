package transform.SymbolTable;
/**
 * Abstract class for elements in the symbol table.
 * 
 * @author mariapaquin
 *
 */
public abstract class SymbolSTE {

	protected String name;
	protected SymbolType type;
	protected SymbolTable symbolTable;

	/**
	 * Create a new symbol table element. 
	 * 
	 * @param name Name of the element.
	 * @param type Type of the element.
	 */
	public SymbolSTE(String name,  SymbolType type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Get the symbol table associated with the element.
	 * 
	 * @return SymbolTable
	 */
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	/**
	 * Set the element's symbol table. 
	 * 
	 * @param symbolTable SymbolTable (scope) enclosing the element.
	 */
	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	/**
	 * Get the name of the element.
	 * 
	 * @return Name of the element.
	 */
	public String getName() {
		return name;
	}

}
