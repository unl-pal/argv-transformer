package unit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import util.TypeResolutionUtils;

public class TypeResolutionUtilsTest {

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
	public void isStringTypeOnlyMatchesSimpleStringName() {
		assertTrue(TypeResolutionUtils.isStringType(parseParamType("String")));
		assertFalse(TypeResolutionUtils.isStringType(parseParamType("int")));
		assertFalse(TypeResolutionUtils.isStringType(null));
	}

	@Test
	public void integerTypeCodeCoversAllIntegerPrimitives() {
		assertTrue(TypeResolutionUtils.isIntegerTypeCode(parseParamType("int")));
		assertTrue(TypeResolutionUtils.isIntegerTypeCode(parseParamType("long")));
		assertTrue(TypeResolutionUtils.isIntegerTypeCode(parseParamType("short")));
		assertTrue(TypeResolutionUtils.isIntegerTypeCode(parseParamType("byte")));
		assertTrue(TypeResolutionUtils.isIntegerTypeCode(parseParamType("char")));
		assertFalse(TypeResolutionUtils.isIntegerTypeCode(parseParamType("double")));
	}

	@Test
	public void numericTypeCodeCombinesIntegerAndFloatingPoint() {
		assertTrue(TypeResolutionUtils.isNumericTypeCode(parseParamType("int")));
		assertTrue(TypeResolutionUtils.isNumericTypeCode(parseParamType("double")));
		assertTrue(TypeResolutionUtils.isNumericTypeCode(parseParamType("float")));
		assertFalse(TypeResolutionUtils.isNumericTypeCode(parseParamType("boolean")));
	}

	@Test
	public void booleanAndVoidTypeCodesAreExclusive() {
		assertTrue(TypeResolutionUtils.isBooleanTypeCode(parseParamType("boolean")));
		assertFalse(TypeResolutionUtils.isBooleanTypeCode(parseParamType("int")));
		assertTrue(TypeResolutionUtils.isVoidTypeCode(parseParamType("void")));
		assertFalse(TypeResolutionUtils.isVoidTypeCode(parseParamType("int")));
	}

	@Test
	public void integerOrIntegerArrayTypeCodeRecursesThroughArrayElement() {
		assertTrue(TypeResolutionUtils.isIntegerOrIntegerArrayTypeCode(parseParamType("int[]")));
		assertTrue(TypeResolutionUtils.isIntegerOrIntegerArrayTypeCode(parseParamType("int[][]")));
		assertFalse(TypeResolutionUtils.isIntegerOrIntegerArrayTypeCode(parseParamType("String[]")));
	}

	@Test
	public void booleanOrBooleanArrayTypeCodeRecursesThroughArrayElement() {
		assertTrue(TypeResolutionUtils.isBooleanOrBooleanArrayTypeCode(parseParamType("boolean[]")));
		assertFalse(TypeResolutionUtils.isBooleanOrBooleanArrayTypeCode(parseParamType("int[]")));
	}

	private String invocationSignature(Expression expr) {
		MethodInvocation invocation = (MethodInvocation) expr;
		return invocation.getExpression() + "." + invocation.getName();
	}

	@Test
	public void generateBooleanFromTargetDispatchesOnTargetString() {
		AST ast = AST.newAST(AST.JLS8);
		assertEquals("Verifier.nondetBoolean",
				invocationSignature(TypeResolutionUtils.generateBooleanFromTarget(ast, false, "SVCOMP")));
		assertEquals("Debug.makeSymbolicBoolean",
				invocationSignature(TypeResolutionUtils.generateBooleanFromTarget(ast, false, "SPF")));
		assertEquals("rand.nextBoolean",
				invocationSignature(TypeResolutionUtils.generateBooleanFromTarget(ast, false, "anythingElse")));
	}

	@Test
	public void generateIntegerFromTargetDispatchesOnTargetString() {
		AST ast = AST.newAST(AST.JLS8);
		assertEquals("Verifier.nondetInt",
				invocationSignature(TypeResolutionUtils.generateIntegerFromTarget(ast, false, "SVCOMP")));
		assertEquals("Debug.makeSymbolicInteger",
				invocationSignature(TypeResolutionUtils.generateIntegerFromTarget(ast, false, "SPF")));
		assertEquals("rand.nextInt",
				invocationSignature(TypeResolutionUtils.generateIntegerFromTarget(ast, false, "anythingElse")));
	}

	@Test
	public void generateDoubleFromTargetDispatchesOnTargetString() {
		AST ast = AST.newAST(AST.JLS8);
		assertEquals("Verifier.nondetDouble",
				invocationSignature(TypeResolutionUtils.generateDoubleFromTarget(ast, false, "SVCOMP")));
		assertEquals("Debug.makeSymbolicReal",
				invocationSignature(TypeResolutionUtils.generateDoubleFromTarget(ast, false, "SPF")));
		assertEquals("rand.nextDouble",
				invocationSignature(TypeResolutionUtils.generateDoubleFromTarget(ast, false, "anythingElse")));
	}

	@Test
	public void createSymbolicArgumentForPrimitiveTypeReturnsNondetCall() {
		AST ast = AST.newAST(AST.JLS8);
		Type intType = parseParamType("int");
		// Re-associate the type with a fresh AST since ASTs can't mix nodes across parsers.
		Expression result = TypeResolutionUtils.createSymbolicArgument(
				(Type) org.eclipse.jdt.core.dom.ASTNode.copySubtree(ast, intType), ast, false);
		assertEquals("Verifier.nondetInt", invocationSignature(result));
	}

	@Test
	public void createSymbolicArgumentForNullBindingReturnsNullLiteral() {
		AST ast = AST.newAST(AST.JLS8);
		Expression result = TypeResolutionUtils.createSymbolicArgument((org.eclipse.jdt.core.dom.ITypeBinding) null, ast, false);
		assertTrue(result instanceof org.eclipse.jdt.core.dom.NullLiteral);
	}

	@Test
	public void isCheckedExceptionDefaultsTrueWhenBindingUnknown() {
		assertTrue("an unresolvable binding should conservatively be treated as checked",
				TypeResolutionUtils.isCheckedException(null));
	}
}
