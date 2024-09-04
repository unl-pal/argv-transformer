package transform.SymbolTable;
/**
 * Represents a class in the symbol table.
 * 
 * @author mariapaquin
 *
 */
public class ClassSTE extends SymbolSTE{

	/**
	 * Create a new ClassSTE.
	 * 
	 * @param name Name of the class.
	 */
	public ClassSTE(String name) {
		super(name, SymbolType.CLASS_STE);
	}
}
