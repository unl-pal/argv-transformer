package transform.SymbolTable;

import org.eclipse.jdt.core.dom.Type;
/**
 * Represents a method in the symbol table.
 * 
 * @author mariapaquin
 *
 */
public class MethodSTE extends SymbolSTE{
	private Type returnType;

	/**
	 * Create a new MethodSTE.
	 * 
	 * @param name Name of the method, which is uniquely represented
	 * by its name along with parameter types.
	 */
	public MethodSTE(String name) {
		super(name, SymbolType.METHOD_STE);
	}
	
	/**
	 * Get the return type of the method.
	 * 
	 * @return Return type of the method.
	 */
	public Type getReturnType() {
		return returnType;
	}
	
	/**
	 * Set the return type of the method.
	 * 
	 * @param returnType Return type of the method.
	 */
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
}
