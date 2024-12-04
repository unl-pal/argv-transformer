package sourceAnalysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * Represents a method in a Java class. Used to keep track of whether the 
 * method is suitable for symbolic execution.
 *
 * @author mariapaquin
 *
 */
public class AnalyzedMethod {

	private String name;
	private MethodDeclaration node;
	private boolean hasParameters;
	private boolean hasOnlyTypeParameters;
	private int typeParameterCount;
	private boolean hasTypeOperations;
	private int typeOperationCount;
	private boolean hasTypeConditional;
	private int typeConditionalCount;
	private boolean hasLoop;

		
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
	
	public boolean hasParameters() {
		return hasParameters;
	}

	public void setHasParameters(boolean hasParameters) {
		this.hasParameters = hasParameters;
	}
	
	public void setHasOnlyTypeParameters(boolean hasOnlyTypeParameters) {
		this.hasOnlyTypeParameters = hasOnlyTypeParameters;
	}
	
	public void setHasTypeOperations(boolean hasTypeOperations) {
		this.hasTypeOperations = hasTypeOperations;
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
	
	public boolean isSymbolicSuitable() {
//		return (hasParameters && hasOnlyIntParameters && hasConditional);
		return (hasParameters && hasOnlyTypeParameters && hasTypeOperations);
	}

	public void setHasTypeConditional(boolean hasTypeConditional) {
		this.hasTypeConditional = hasTypeConditional;
		
	}
	
	public boolean isHasTypeConditional() {
		return hasTypeConditional;
	}
	
	public void setHasLoop(boolean hasLoop) {
		this.hasLoop = hasLoop;
	}
	
	public boolean isHasLoop() {
		return hasLoop;
	}
}
