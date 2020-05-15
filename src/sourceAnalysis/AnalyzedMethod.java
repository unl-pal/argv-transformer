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
	private boolean hasOnlyIntParameters;
	private int intParameterCount;
	private boolean hasIntOperations;
	private int intOperationCount;
	private boolean hasConditional;
	private int conditionalCount;
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
	
	public int getConditionalCount() {
		return conditionalCount;
	}
	
	public void setConditionalCount(int conditionalCount) {
		this.conditionalCount = conditionalCount;
	}
	
	public int getIntParameterCount() {
		return intParameterCount;
	}
	
	public void setIntParameterCount(int intParameterCount) {
		this.intParameterCount = intParameterCount;
	}
	
	public boolean isSymbolicSuitable() {
//		return (hasParameters && hasOnlyIntParameters && hasConditional);
		return (hasParameters && hasOnlyIntParameters && hasIntOperations);
	}

	public void setHasConditional(boolean hasConditional) {
		this.hasConditional = hasConditional;
		
	}
	
	public boolean isHasConditional() {
		return hasConditional;
	}
	
	public void setHasLoop(boolean hasLoop) {
		this.hasLoop = hasLoop;
	}
	
	public boolean isHasLoop() {
		return hasLoop;
	}
}
