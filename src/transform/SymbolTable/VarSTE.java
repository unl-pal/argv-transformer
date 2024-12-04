package transform.SymbolTable;

import org.eclipse.jdt.core.dom.Type;
/**
 * Class representing a variable in the symbol table.
 * 
 * @author mariapaquin
 *
 */
public class VarSTE extends SymbolSTE {
	private Type type;
	private boolean fieldVar;

	/**
	 * Create a new VarSTE.
	 * 
	 * @param name Name of the variable.
	 * @param type Type of the variable.
	 */
	public VarSTE(String name, Type type) {
		super(name, SymbolType.VAR_STE);
		this.type = type;
		fieldVar = false;
	}
	
	/**
	 * Get the type of the variable.
	 * 
	 * @return Type of the variable.
	 */
	public Type getVarType() {
		return type;
	}
	
	/**
	 * Set the type of the variable.
	 * 
	 * @param type Type of the variable.
	 */
	public void setVarType(Type type) {
		this.type = type;
	}

	/**
	 * Whether the variable is a field variable.
	 * 
	 * @return true if the variable is a field variable, false otherwise.
	 */
	public boolean isFieldVar() {
		return fieldVar;
	}

	/**
	 * Set whether the variable is a field variable.
	 * 
	 * @param fieldVar If the variable is a field variable.
	 */
	public void setFieldVar(boolean fieldVar) {
		this.fieldVar = fieldVar;
	}
}
