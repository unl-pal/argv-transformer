package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Paths;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.junit.Test;

import transform.Transformer;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeTable;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeTableVisitor;

public class TypeTableVisitorTest {

	/**
	 * Builds a TypeTable the same way the real pipeline does (Transformer's
	 * transformFiles/SuitableMethodFinder.analyze): a TypeChecker is shared by
	 * SymbolTableVisitor (which builds the scope tree) and TypeTableVisitor,
	 * run over the same CompilationUnit. A resolvable environment is required
	 * because several visit methods here call
	 * resolveTypeBinding()/resolveMethodBinding() directly.
	 */
	private CompilationUnit[] cuHolder = new CompilationUnit[1];

	private TypeTable buildTypeTable(String source) {
		String[] classPath = {
				Paths.get("build", "classes", "java", "main").toString(),
				Paths.get("build", "classes", "java", "test").toString() };
		String[] sourcePath = { Paths.get("src", "java").toString(), Paths.get("src", "java").toString() };
		ASTParser parser = Transformer.getParser(source, sourcePath, classPath, new File("TestClass.java"));
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cuHolder[0] = cu;

		TypeChecker typeChecker = new TypeChecker();

		SymbolTableVisitor symTableVisitor = new SymbolTableVisitor(typeChecker);
		cu.accept(symTableVisitor);
		SymbolTable root = symTableVisitor.getRoot();

		TypeTableVisitor typeTableVisitor = new TypeTableVisitor(root, typeChecker);
		cu.accept(typeTableVisitor);
		return typeTableVisitor.getTypeTable();
	}

	private MethodDeclaration firstMethod(CompilationUnit cu) {
		return (MethodDeclaration) ((TypeDeclaration) cu.types().get(0)).bodyDeclarations().get(0);
	}

	private Expression firstStatementExpression(MethodDeclaration method) {
		return ((ExpressionStatement) method.getBody().statements().get(0)).getExpression();
	}

	private PrimitiveType.Code codeOf(Type type) {
		assertNotNull("expected a resolved type", type);
		return ((PrimitiveType) type).getPrimitiveTypeCode();
	}

	@Test
	public void integerLiteralIsTypedAsInt() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        int x = 5;\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(0);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		NumberLiteral literal = (NumberLiteral) fragment.getInitializer();

		assertEquals(PrimitiveType.INT, codeOf(table.getNodeType(literal)));
	}

	@Test
	public void floatingPointLiteralIsTypedAsFloat() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        double x = 5.5;\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(0);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		NumberLiteral literal = (NumberLiteral) fragment.getInitializer();

		// NumberLiteral typing doesn't distinguish precision: any token
		// containing '.' is typed FLOAT, regardless of the declared type.
		assertEquals(PrimitiveType.FLOAT, codeOf(table.getNodeType(literal)));
	}

	@Test
	public void stringLiteralIsTypedAsStringSimpleType() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        String s = \"hi\";\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(0);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		StringLiteral literal = (StringLiteral) fragment.getInitializer();

		Type type = table.getNodeType(literal);
		assertNotNull(type);
		assertEquals("String", type.toString());
	}

	@Test
	public void booleanLiteralIsTypedAsBoolean() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        boolean b = true;\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(0);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		BooleanLiteral literal = (BooleanLiteral) fragment.getInitializer();

		assertEquals(PrimitiveType.BOOLEAN, codeOf(table.getNodeType(literal)));
	}

	@Test
	public void additionOfTwoIntsIsTypedAsInt() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        int a = 1;\n" +
				"        int b = 2;\n" +
				"        int c = a + b;\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(2);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		InfixExpression infix = (InfixExpression) fragment.getInitializer();

		assertEquals(PrimitiveType.INT, codeOf(table.getNodeType(infix)));
	}

	@Test
	public void relationalComparisonOfIntsIsTypedAsBoolean() {
		String source =
				"public class TestClass {\n" +
				"    boolean m(int a, int b) {\n" +
				"        return a > b;\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		org.eclipse.jdt.core.dom.ReturnStatement ret =
				(org.eclipse.jdt.core.dom.ReturnStatement) method.getBody().statements().get(0);
		InfixExpression infix = (InfixExpression) ret.getExpression();

		assertEquals(PrimitiveType.BOOLEAN, codeOf(table.getNodeType(infix)));
	}

	@Test
	public void conditionalExpressionTakesTypeOfThenBranch() {
		String source =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"        int x = flag ? 1 : 2;\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(0);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		ConditionalExpression cond = (ConditionalExpression) fragment.getInitializer();

		assertEquals(PrimitiveType.INT, codeOf(table.getNodeType(cond)));
	}

	@Test
	public void makeSymbolicIntegerInvocationIsTypedAsInt() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        int x = Debug.makeSymbolicInteger(\"x0\");\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(0);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		MethodInvocation invocation = (MethodInvocation) fragment.getInitializer();

		assertEquals(PrimitiveType.INT, codeOf(table.getNodeType(invocation)));
	}

	@Test
	public void simpleNameReferencingIntVariableResolvesToInt() {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        int a = 1;\n" +
				"        int b = a;\n" +
				"    }\n" +
				"}";
		TypeTable table = buildTypeTable(source);
		MethodDeclaration method = firstMethod(cuHolder[0]);
		VariableDeclarationStatement decl = (VariableDeclarationStatement) method.getBody().statements().get(1);
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) decl.fragments().get(0);
		SimpleName ref = (SimpleName) fragment.getInitializer();

		assertEquals(PrimitiveType.INT, codeOf(table.getNodeType(ref)));
	}
}
