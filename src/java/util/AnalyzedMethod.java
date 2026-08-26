package util;

import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * Represents a method in a Java class. Tracks the type-matching expression,
 * conditional, and parameter counts used by filter/transform's suitability
 * thresholds.
 *
 * @author mariapaquin
 *
 */
public class AnalyzedMethod {

	private final String name;
	private final MethodDeclaration node;
	private int typeParameterCount;
	private int typeOperationCount;
	private int typeConditionalCount;


	public AnalyzedMethod(MethodDeclaration node) {
		this.node = node;
		name = node.getName().getIdentifier();
	}

	public String getName() {
		return name;
	}

	public MethodDeclaration getMethodDeclaration() {
		return node;
	}

	public int getTypeOperationCount() {
		return typeOperationCount;
	}

	public void setTypeOperationCount(int typeOperationCount) {
		this.typeOperationCount = typeOperationCount;
	}

	public int getTypeConditionalCount() {
		return typeConditionalCount;
	}

	public void setConditionalCount(int typeConditionalCount) {
		this.typeConditionalCount = typeConditionalCount;
	}

	public int getTypeParameterCount() {
		return typeParameterCount;
	}

	public void setTypeParameterCount(int typeParameterCount) {
		this.typeParameterCount = typeParameterCount;
	}
}
