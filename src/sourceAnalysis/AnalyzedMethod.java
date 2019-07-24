package sourceAnalysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * This class represents a method in a Java class. It's used to 
 * keep track of whether the method is symbolic suitable, and 
 * other relevant information.
 *
 * @author mariapaquin
 *
 */
public class AnalyzedMethod {

	private String name;
	private MethodDeclaration node;
	private boolean hasParameters;
	private boolean hasOnlyIntParameters;
	private int intParameterCount;
	private boolean hasIntOperations;
	private int intOperationCount;
	private boolean hasConditional;

		
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
	
	public boolean isHasParameters() {
		return hasParameters;
	}

	public void setHasParameters(boolean hasParameters) {
		this.hasParameters = hasParameters;
	}
	
	public void setHasOnlyIntParameters(boolean hasOnlyIntParameters) {
		this.hasOnlyIntParameters = hasOnlyIntParameters;
	}
	
	public void setHasIntOperations(boolean hasIntOperations) {
		this.hasIntOperations = hasIntOperations;
	}
	
	public int getIntOperationCount() {
		return intOperationCount;
	}
	
	public void setIntOperationCount(int intOperationCount) {
		this.intOperationCount = intOperationCount;
	}
	
	public int getIntParameterCount() {
		return intParameterCount;
	}
	
	public void setIntParameterCount(int intParameterCount) {
		this.intParameterCount = intParameterCount;
	}
	
	public boolean isSymbolicSuitable() {
		return (hasParameters && hasOnlyIntParameters && hasConditional);
//		return (hasParameters && hasOnlyIntParameters && hasIntOperations);
	}

	public void setHasConditional(boolean hasConditional) {
		this.hasConditional = hasConditional;
		
	}
}
