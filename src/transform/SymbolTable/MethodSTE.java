package transform.SymbolTable;

import java.util.LinkedList;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class MethodSTE extends SymbolSTE{
	private LinkedList<SingleVariableDeclaration> parameters;
	private Type returnType;

	public MethodSTE(String name) {
		super(name, SymbolType.METHOD_STE);
		parameters = new LinkedList<SingleVariableDeclaration>();
	}
	
	public LinkedList<SingleVariableDeclaration> getParameters() {
		return parameters;
	}

	public void setParameters(LinkedList<SingleVariableDeclaration> parameters) {
		this.parameters = parameters;
	}
	
	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	
	public void addParam(SingleVariableDeclaration paramToAdd) {
		parameters.add(paramToAdd);
	}

	public void removeParam(SingleVariableDeclaration paramToRemove) {
		for(SingleVariableDeclaration param: parameters) {
			if(param.getName().getIdentifier().equals(paramToRemove.getName().getIdentifier())) {
				parameters.remove(param);
				break;
			}
		}
	}
}
