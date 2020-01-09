package transform.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import transform.TypeChecking.TypeChecker;
/**
 * Visitor class for collecting resolvable types. Creates a TypeChecker,
 * which will keep track of the types that are allowed in the generated 
 * benchmark.
 *  
 * @author mariapaquin
 *
 */
public class TypeCollectVisitor  extends ASTVisitor {
	private TypeChecker typeChecker;

	/**
	 * Create a new TypeCollectVisitor. 
	 */
	public TypeCollectVisitor() {
		typeChecker = new TypeChecker();
	}
	
	/**
	 * Add to allowed types the classes imported from the java standard library
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		String importName = node.getName().getFullyQualifiedName();
		String[] importSplit = importName.split("\\.");
		String className = importSplit[importSplit.length - 1];
		if (importName.startsWith("java.") || importName.startsWith("javax.")) {
			if (!node.toString().contains(".*;")) {
				typeChecker.addJavaImportType(className);
			}
		}
		return true;
	}
	
	/**
	 * Add to allowed types inner classes and the class itself
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		typeChecker.addClassType(node.getName().getIdentifier());
		return true;
	}
	
	/**
	 * 
	 * @return TypeChecker
	 */
	public TypeChecker getTypeChecker() {
		return typeChecker;
	}
}
