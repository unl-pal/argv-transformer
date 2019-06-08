package transform.SymbolTable;

import java.util.HashMap;

public class SymbolTable {
	protected SymbolTable parent;
	protected String id;
	protected HashMap<String, SymbolSTE> table;
	
	public SymbolTable(SymbolTable parent) {
		table = new HashMap<String, SymbolSTE>();
		this.parent = parent;
	}
	
	public void put(String name, SymbolSTE sym) {
		table.put(name, sym);
	}
	
	public SymbolTable getParent() {
		return parent;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public VarSTE getFieldVarSTE(String name) {
		VarSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof VarSTE && ((VarSTE) sym).isFieldVar()) {	
				found = (VarSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
	public VarSTE getVarSTE(String name) {
		VarSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof VarSTE) {
				found = (VarSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
	public MethodSTE getMethodSTE(String name) {
		MethodSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof MethodSTE) {
				found = (MethodSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
	public ClassSTE getClassSTE(String name) {
		ClassSTE found = null;
		SymbolTable currScope = this;
		
		while(currScope != null && found == null) {
			HashMap<String, SymbolSTE> currTable = currScope.getTable();
			SymbolSTE sym = currTable.get(name);
			if(sym instanceof ClassSTE) {
				found = (ClassSTE) currTable.get(name);
			}
			currScope = currScope.getParent();
		}
		return found;
	}
	
//	public BlockSTE getInnerBlockSTE(String name) {
//		BlockSTE found = null;
//		SymbolTable currScope = this;
//		
//		return found;
//	}
	
	public HashMap<String, SymbolSTE> getTable(){
		return table;
	}

}
