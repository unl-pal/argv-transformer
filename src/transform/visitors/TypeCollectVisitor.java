package transform.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import transform.TypeChecking.TypeChecker;
/**
 * Visitor class for collecting resolvable types.
 *  
 * In the current implementation, we are only allowing primitive types, 
 * but in subsequent versions we will allow for types from the JDK, inner 
 * class types and the class itself.
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
	
	@Override
	public boolean visit(ImportDeclaration node) {
		String importName = node.getName().getFullyQualifiedName();
		String[] importSplit = importName.split("\\.");
		String className = importSplit[importSplit.length - 1];
		if (importName.startsWith("java.") || importName.startsWith("javax.")) {
			// TODO: The visitor freaks out in the presence of ArrayList types. Haven't
			// figured out why yet...
			if (!node.toString().contains(".*;") && !node.toString().equals("ArrayList")) {
				typeChecker.addJavaImportType(className);
			}
		}
		return true;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
	//	typeChecker.addClassType(node.getName().getIdentifier());
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
