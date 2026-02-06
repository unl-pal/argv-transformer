package sourceAnalysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import java.util.HashMap;

/**
 * Represents a method in a Java class. Used to keep track of whether the 
 * method is suitable for symbolic execution.
 *
 * @author mariapaquin
 *
 */
public class AnalyzedMethod {

	private String name;
  private HashMap<String, Integer> opCounts = new HashMap<>(); //string method invocation counts
	private MethodDeclaration node;
	// private boolean hasParameters;
	private boolean hasOnlyTypeParameters;
	private int typeParameterCount;
	// private boolean hasTypeOperations;
	private int typeOperationCount;
	// private boolean hasTypeConditional;
	private int typeConditionalCount;
	private boolean hasLoop;

		
	public AnalyzedMethod(MethodDeclaration node) {
		this.node = node;
		name = node.getName().getIdentifier();
	}
	
	public String getName() {
		return name;
	}

  public void setName(String name) {
    this.name = name;
  }
	
	public MethodDeclaration getMethodDeclaration() {
		return node;
	}

  public void setOpCounts(HashMap<String, Integer> opCounts) {
    this.opCounts = opCounts;
  }

  public HashMap<String, Integer> getOpCounts() {
    return opCounts;
  }

  public int getTotalOpCount() {
    return opCounts.values().stream().mapToInt(Integer::intValue).sum();
  }
	
	// public boolean hasParameters() {
	// 	return hasParameters;
	// }
	//
	// public void setHasParameters(boolean hasParameters) {
	// 	this.hasParameters = hasParameters;
	// }
	
	public void setHasOnlyTypeParameters(boolean hasOnlyTypeParameters) {
		this.hasOnlyTypeParameters = hasOnlyTypeParameters;
	}

  public boolean getHasOnlyTypeParameters() {
    return hasOnlyTypeParameters;
  }

	// public void setHasTypeOperations(boolean hasTypeOperations) {
	// 	this.hasTypeOperations = hasTypeOperations;
	// }
	
	public int getTypeOperationCount() {
		return typeOperationCount;
	}
	
	public void setTypeOperationCount(int typeOperationCount) {
		this.typeOperationCount = typeOperationCount;
	}
	
	public int getTypeConditionalCount() {
		return typeConditionalCount;
	}
	
	public void setTypeConditionalCount(int typeConditionalCount) {
		this.typeConditionalCount = typeConditionalCount;
	}
	
	public int getTypeParameterCount() {
		return typeParameterCount;
	}
	
	public void setTypeParameterCount(int typeParameterCount) {
		this.typeParameterCount = typeParameterCount;
	}
	
  // stricter
  // maybe shuold require param exists?
	// public boolean isSymbolicSuitable() {
//		return (hasParameters && hasOnlyIntParameters && hasConditional);
		// return (hasParameters && hasOnlyTypeParameters && hasTypeOperations);
	// }

  public boolean isSuitable(int minExpr, int minCond, int minParam) {
    return (typeOperationCount >= minExpr &&
            typeConditionalCount >= minCond &&
            typeParameterCount >= minParam);
  }

	// public void setHasTypeConditional(boolean hasTypeConditional) {
	// 	this.hasTypeConditional = hasTypeConditional;
	//
	// }
	//
	// public boolean isHasTypeConditional() {
	// 	return hasTypeConditional;
	// }
	
	public void setHasLoop(boolean hasLoop) {
		this.hasLoop = hasLoop;
	}
	
	public boolean isHasLoop() {
		return hasLoop;
	}

  public void incrementTypeOperationCount() {
    typeOperationCount++;
  }

  public void incrementTypeConditionalCount() {
    typeConditionalCount++;
  }
}
