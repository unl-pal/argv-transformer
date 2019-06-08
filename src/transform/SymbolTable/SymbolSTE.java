package transform.SymbolTable;

public class SymbolSTE {

	protected String name;
	protected SymbolType type;
	protected SymbolTable symbolTable;


	public SymbolSTE(String name,  SymbolType type) {
		this.name = name;
		this.type = type;
	}
	
	
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}


	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}


	public String getName() {
		return name;
	}

}
