package unit.transform.SymbolTable;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;

public class SymbolTableTest {

	@Test
	public void getVarSTEFindsSymbolInOwnScope() {
		SymbolTable scope = new SymbolTable(null);
		VarSTE var = new VarSTE("x", null);
		scope.put("x", var);

		assertSame(var, scope.getVarSTE("x"));
	}

	@Test
	public void getVarSTELooksUpEnclosingScopeWhenNotFoundLocally() {
		SymbolTable parent = new SymbolTable(null);
		VarSTE var = new VarSTE("x", null);
		parent.put("x", var);

		SymbolTable child = new SymbolTable(parent);

		assertSame("should walk up to the parent scope", var, child.getVarSTE("x"));
	}

	@Test
	public void getVarSTEPrefersClosestScopeOnShadowing() {
		SymbolTable parent = new SymbolTable(null);
		VarSTE outer = new VarSTE("x", null);
		parent.put("x", outer);

		SymbolTable child = new SymbolTable(parent);
		VarSTE inner = new VarSTE("x", null);
		child.put("x", inner);

		assertSame("inner declaration should shadow the outer one", inner, child.getVarSTE("x"));
	}

	@Test
	public void getVarSTEReturnsNullWhenUndeclaredAnywhere() {
		SymbolTable scope = new SymbolTable(new SymbolTable(null));
		assertNull(scope.getVarSTE("doesNotExist"));
	}

	@Test
	public void getFieldVarSTEOnlyMatchesVarsMarkedAsFields() {
		SymbolTable scope = new SymbolTable(null);
		VarSTE localVar = new VarSTE("x", null);
		localVar.setFieldVar(false);
		scope.put("x", localVar);

		assertNull("a non-field variable should not be returned as a field", scope.getFieldVarSTE("x"));

		VarSTE fieldVar = new VarSTE("x", null);
		fieldVar.setFieldVar(true);
		scope.put("x", fieldVar);

		assertSame(fieldVar, scope.getFieldVarSTE("x"));
	}

	@Test
	public void getMethodSTEIgnoresNonMethodSymbolsWithSameName() {
		SymbolTable parent = new SymbolTable(null);
		parent.put("thing", new VarSTE("thing", null));

		SymbolTable child = new SymbolTable(parent);
		MethodSTE method = new MethodSTE("thing");
		child.put("thing", method);

		assertSame(method, child.getMethodSTE("thing"));
		// In the parent scope, "thing" is a VarSTE, not a MethodSTE, so it shouldn't match.
		assertNull(parent.getMethodSTE("thing"));
	}

	@Test
	public void getClassSTEWalksUpToEnclosingScope() {
		SymbolTable parent = new SymbolTable(null);
		ClassSTE classSte = new ClassSTE("Outer");
		parent.put("Outer", classSte);

		SymbolTable child = new SymbolTable(parent);

		assertSame(classSte, child.getClassSTE("Outer"));
	}

	@Test
	public void getParentReturnsConstructorArgument() {
		SymbolTable parent = new SymbolTable(null);
		SymbolTable child = new SymbolTable(parent);

		assertSame(parent, child.getParent());
		assertNull(parent.getParent());
	}
}
