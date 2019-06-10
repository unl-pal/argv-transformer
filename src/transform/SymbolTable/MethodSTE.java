package transform.SymbolTable;

import org.eclipse.jdt.core.dom.Type;

public class MethodSTE extends SymbolSTE{
	private Type returnType;

	public MethodSTE(String name) {
		super(name, SymbolType.METHOD_STE);
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
}
