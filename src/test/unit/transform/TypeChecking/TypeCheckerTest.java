package unit.transform.TypeChecking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeChecker.CType;

public class TypeCheckerTest {

	/**
	 * Parses a single-parameter method declaration and returns the parameter's
	 * Type node, so tests can exercise TypeChecker's classification helpers
	 * without needing resolved bindings.
	 */
	private Type parseParamType(String paramDeclaration) {
		String source =
				"public class Sample {\n" +
				"    public void method(" + paramDeclaration + " p) {}\n" +
				"}";
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		MethodDeclaration method = (MethodDeclaration)
				((TypeDeclaration) cu.types().get(0)).bodyDeclarations().get(0);
		SingleVariableDeclaration param = (SingleVariableDeclaration) method.parameters().get(0);
		return param.getType();
	}

	@Test
	public void isIntegerTypeRecognizesAllIntegerPrimitives() {
		assertTrue(TypeChecker.isIntegerType(parseParamType("int")));
		assertTrue(TypeChecker.isIntegerType(parseParamType("long")));
		assertTrue(TypeChecker.isIntegerType(parseParamType("short")));
		assertTrue(TypeChecker.isIntegerType(parseParamType("byte")));
		assertFalse(TypeChecker.isIntegerType(parseParamType("double")));
		assertFalse(TypeChecker.isIntegerType(null));
	}

	@Test
	public void isRealTypeRecognizesFloatingPointPrimitives() {
		assertTrue(TypeChecker.isRealType(parseParamType("double")));
		assertTrue(TypeChecker.isRealType(parseParamType("float")));
		assertFalse(TypeChecker.isRealType(parseParamType("int")));
	}

	@Test
	public void isBooleanTypeRecognizesOnlyBoolean() {
		assertTrue(TypeChecker.isBooleanType(parseParamType("boolean")));
		assertFalse(TypeChecker.isBooleanType(parseParamType("int")));
	}

	@Test
	public void isVoidTypeRecognizesOnlyVoid() {
		assertTrue(TypeChecker.isVoidType(parseParamType("void")));
		assertFalse(TypeChecker.isVoidType(parseParamType("int")));
	}

	@Test
	public void isStringTypeRecognizesStringStringBufferAndChar() {
		assertTrue(TypeChecker.isStringType(parseParamType("String")));
		assertTrue(TypeChecker.isStringType(parseParamType("StringBuffer")));
		assertTrue(TypeChecker.isStringType(parseParamType("char")));
		assertFalse(TypeChecker.isStringType(parseParamType("int")));
		assertFalse(TypeChecker.isStringType(null));
	}

	@Test
	public void checkTypeClassifiesEachCategory() {
		assertEquals(CType.INT, TypeChecker.checkType(parseParamType("int")));
		assertEquals(CType.BOOLEAN, TypeChecker.checkType(parseParamType("boolean")));
		assertEquals(CType.REAL, TypeChecker.checkType(parseParamType("double")));
		assertEquals(CType.STRING, TypeChecker.checkType(parseParamType("String")));
		assertEquals(CType.ANY, TypeChecker.checkType(parseParamType("Object")));
	}

	@Test
	public void integerAndRealArrayTypeIgnoreDepth() {
		// JDT's ArrayType#getElementType() always resolves straight to the
		// innermost non-array element type regardless of depth, so array
		// dimension isn't (and can't be) distinguished here.
		assertTrue(TypeChecker.isIntegerArrayType(parseParamType("int[]")));
		assertTrue(TypeChecker.isIntegerArrayType(parseParamType("int[][]")));
		assertFalse(TypeChecker.isIntegerArrayType(parseParamType("double[]")));
		assertFalse(TypeChecker.isIntegerArrayType(parseParamType("int")));

		assertTrue(TypeChecker.isRealArrayType(parseParamType("double[]")));
		assertTrue(TypeChecker.isRealArrayType(parseParamType("double[][]")));
		assertFalse(TypeChecker.isRealArrayType(parseParamType("int[]")));
	}

	@Test
	public void allowedTypePermitsPrimitivesAndRejectsUnresolvedUserTypes() {
		TypeChecker checker = new TypeChecker();
		assertTrue(checker.allowedType(parseParamType("int")));
		// A simple type with an unresolvable binding (no bindings requested by the
		// parser) falls back to "unknown", which should not be allowed.
		assertFalse(checker.allowedType(parseParamType("MyCustomType")));
		assertFalse(checker.allowedType((Type) null));
	}

	@Test
	public void allowedTypeRecursesIntoArrayElementType() {
		TypeChecker checker = new TypeChecker();
		assertTrue(checker.allowedType(parseParamType("int[]")));
		assertFalse(checker.allowedType(parseParamType("MyCustomType[]")));
	}
}
