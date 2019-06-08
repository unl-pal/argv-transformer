package transform.SymbolTable;

import org.eclipse.jdt.core.dom.Type;

public class VarSTE extends SymbolSTE {
	private Type type;
	private boolean fieldVar;
	private boolean resolvable;

	public VarSTE(String name, Type type) {
		super(name, SymbolType.VAR_STE);
		this.type = type;
		resolvable = true;
	}
	
	public Type getVarType() {
		return type;
	}

	public void setVarType(Type type) {
		this.type = type;
	}

	public boolean isFieldVar() {
		return fieldVar;
	}

	public void setFieldVar(boolean fieldVar) {
		this.fieldVar = fieldVar;
	}
	
	public boolean isResolvable() {
		return resolvable;
	}

	public void setResolvable(boolean resolvable) {
		this.resolvable = resolvable;
	}
}
