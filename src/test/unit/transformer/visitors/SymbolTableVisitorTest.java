package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;
import transform.TypeChecking.TypeChecker;
import transform.visitors.SymbolTableVisitor;

public class SymbolTableVisitorTest {

	private SymbolTable buildRoot(String source) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		SymbolTableVisitor visitor = new SymbolTableVisitor(new TypeChecker());
		cu.accept(visitor);
		return visitor.getRoot();
	}

	@Test
	public void classDeclarationCreatesClassSTEWithNestedScope() {
		String source = "public class TestClass {}";
		SymbolTable root = buildRoot(source);

		ClassSTE classSte = root.getClassSTE("TestClass");
		assertNotNull(classSte);
		assertNotNull("class should get its own nested scope", classSte.getSymbolTable());
		assertEquals(root, classSte.getSymbolTable().getParent());
	}

	@Test
	public void interfaceIsNotAddedToSymbolTable() {
		String source = "public interface TestInterface { void m(); }";
		SymbolTable root = buildRoot(source);

		assertNull("interfaces are skipped entirely", root.getClassSTE("TestInterface"));
	}

	@Test
	public void methodWithPrimitiveParamsGetsMangledNameAndOwnScope() {
		String source =
				"public class TestClass {\n" +
				"    void m(int a, double b) {}\n" +
				"}";
		SymbolTable root = buildRoot(source);

		SymbolTable classScope = root.getClassSTE("TestClass").getSymbolTable();
		MethodSTE method = classScope.getMethodSTE("mid");
		assertNotNull("expected method name mangled with 'i' for int and 'd' for double", method);
		assertNotNull("method should get its own scope", method.getSymbolTable());
		assertEquals(classScope, method.getSymbolTable().getParent());
	}

	@Test
	public void methodParametersAreRegisteredInMethodScope() {
		String source =
				"public class TestClass {\n" +
				"    void m(int a) {}\n" +
				"}";
		SymbolTable root = buildRoot(source);

		SymbolTable classScope = root.getClassSTE("TestClass").getSymbolTable();
		MethodSTE method = classScope.getMethodSTE("mi");
		VarSTE param = method.getSymbolTable().getVarSTE("a");
		assertNotNull("parameter 'a' should be registered in the method's own scope", param);
		assertFalse("parameters are not field variables", param.isFieldVar());
	}

	@Test
	public void methodWithDisallowedParamTypeGetsNoSymbolTableEntry() {
		String source =
				"public class TestClass {\n" +
				"    void m(ExternalType a) {}\n" +
				"}";
		SymbolTable root = buildRoot(source);

		SymbolTable classScope = root.getClassSTE("TestClass").getSymbolTable();
		assertNull("method with a disallowed param type should be skipped entirely",
				classScope.getMethodSTE("m"));
	}

	@Test
	public void fieldDeclarationIsRegisteredAsFieldVarInClassScope() {
		String source =
				"public class TestClass {\n" +
				"    int count;\n" +
				"}";
		SymbolTable root = buildRoot(source);

		SymbolTable classScope = root.getClassSTE("TestClass").getSymbolTable();
		VarSTE field = classScope.getFieldVarSTE("count");
		assertNotNull(field);
		assertTrue(field.isFieldVar());
	}

	@Test
	public void localVariableDeclarationIsRegisteredInEnclosingMethodScope() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        int local = 1;\n" +
				"    }\n" +
				"}";
		SymbolTable root = buildRoot(source);

		SymbolTable classScope = root.getClassSTE("TestClass").getSymbolTable();
		MethodSTE method = classScope.getMethodSTE("m");
		VarSTE local = method.getSymbolTable().getVarSTE("local");
		assertNotNull(local);
		assertFalse("a local variable is not a field", local.isFieldVar());
	}

	@Test
	public void multipleFieldFragmentsAreAllRegistered() {
		String source =
				"public class TestClass {\n" +
				"    int a, b;\n" +
				"}";
		SymbolTable root = buildRoot(source);

		SymbolTable classScope = root.getClassSTE("TestClass").getSymbolTable();
		assertNotNull(classScope.getFieldVarSTE("a"));
		assertNotNull(classScope.getFieldVarSTE("b"));
	}
}
