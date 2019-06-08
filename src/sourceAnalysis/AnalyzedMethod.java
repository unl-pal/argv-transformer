package sourceAnalysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * Keeps track of whether the method is 
 * symbolic suitable, and other relevant 
 * information.
 * 
 * 	(1) Has only integer parameters
 * 	(2) Has integer operations
 * 		-> if so, how many?
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
//		return (hasParameters && hasOnlyIntParameters);
		return (hasParameters && hasOnlyIntParameters && hasIntOperations);
	}
}
