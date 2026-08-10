package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;

import sourceAnalysis.AnalyzedFile;
import sourceAnalysis.AnalyzedMethod;
import transform.Transformer;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeChecker.CType;
import transform.TypeChecking.TypeTable;
import transform.visitors.FinalizerVisitor;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeTableVisitor;

public class FinalizerVisitorTest {

	private static class Result {
		AnalyzedFile analyzedFile;
		String source;
	}

	/**
	 * Runs the same pipeline stage sequence Transformer.transformFiles uses
	 * before invoking FinalizerVisitor: a shared TypeChecker feeds
	 * SymbolTableVisitor -> TypeTableVisitor -> FinalizerVisitor, all over one
	 * CompilationUnit, then applies the rewriter's edits and returns the
	 * resulting source plus the populated AnalyzedFile.
	 */
	private Result runFinalizer(String source, CType type, int minTypeExpr, int minTypeCond, int minTypeParams) throws Exception {
		String[] classPath = {
				Paths.get("build", "classes", "java", "main").toString(),
				Paths.get("build", "classes", "java", "test").toString() };
		String[] sourcePath = { Paths.get("src", "java").toString(), Paths.get("src", "java").toString() };
		ASTParser parser = Transformer.getParser(source, sourcePath, classPath, new File("TestClass.java"));
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.recordModifications();

		TypeChecker typeChecker = new TypeChecker();

		SymbolTableVisitor symTableVisitor = new SymbolTableVisitor(typeChecker);
		cu.accept(symTableVisitor);
		SymbolTable root = symTableVisitor.getRoot();

		TypeTableVisitor typeTableVisitor = new TypeTableVisitor(root, typeChecker);
		cu.accept(typeTableVisitor);
		TypeTable typeTable = typeTableVisitor.getTypeTable();

		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());
		AnalyzedFile af = new AnalyzedFile(new File("TestClass.java"));
		FinalizerVisitor visitor = new FinalizerVisitor(cu.getAST(), rewriter, typeChecker, af, typeTable,
				minTypeExpr, minTypeCond, minTypeParams, type);
		cu.accept(visitor);

		Document document = new Document(source);
		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);

		Result result = new Result();
		result.analyzedFile = af;
		result.source = document.get();
		return result;
	}

	@Test
	public void methodMeetingThresholdsIsMarkedSuitable() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    static int add(int a, int b) {\n" +
				"        return a + b;\n" +
				"    }\n" +
				"}";
		Result result = runFinalizer(source, CType.INT, 1, 0, 1);

		Set<AnalyzedMethod> suitable = result.analyzedFile.getSuitableMethods();
		assertEquals(1, suitable.size());
		assertEquals("add", suitable.iterator().next().getName());
	}

	@Test
	public void methodBelowThresholdIsNotMarkedSuitable() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    static void noop() {\n" +
				"    }\n" +
				"}";
		Result result = runFinalizer(source, CType.INT, 1, 0, 1);

		assertTrue(result.analyzedFile.getSuitableMethods().isEmpty());
	}

	@Test
	public void generatedMainInvokesStaticMethodWithoutInstance() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    static int add(int a, int b) {\n" +
				"        return a + b;\n" +
				"    }\n" +
				"}";
		Result result = runFinalizer(source, CType.INT, 1, 0, 1);

		assertTrue("generated main should call add(...) directly", result.source.contains("add("));
		assertFalse("a static-only class shouldn't need an instance",
				result.source.contains("instance"));
		assertTrue(result.source.contains("public static void main(String[] args)"));
	}

	@Test
	public void generatedMainCreatesInstanceForNonStaticMethod() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    int add(int a, int b) {\n" +
				"        return a + b;\n" +
				"    }\n" +
				"}";
		Result result = runFinalizer(source, CType.INT, 1, 0, 1);

		assertTrue("an instance should be created for a non-static method",
				result.source.contains("TestClass instance = new TestClass()"));
		assertTrue("the invocation should be on the instance",
				result.source.contains("instance.add("));
	}

	@Test
	public void generatedMainInvokesEveryNonConstructorMethodRegardlessOfSuitability() throws Exception {
		// main-method generation invokes every non-constructor, non-main method
		// unconditionally -- it isn't filtered by the suitability thresholds
		// that getSuitableMethods() applies.
		String source =
				"public class TestClass {\n" +
				"    static void noop() {\n" +
				"    }\n" +
				"}";
		Result result = runFinalizer(source, CType.INT, 1, 0, 1);

		assertTrue(result.analyzedFile.getSuitableMethods().isEmpty());
		assertTrue("main should still invoke noop() even though it isn't 'suitable'",
				result.source.contains("noop()"));
	}

	@Test
	public void constructorParametersBecomeSymbolicArgumentsForInstanceCreation() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    TestClass(int seed) {\n" +
				"    }\n" +
				"    int add(int a, int b) {\n" +
				"        return a + b;\n" +
				"    }\n" +
				"}";
		Result result = runFinalizer(source, CType.INT, 1, 0, 1);

		assertTrue("constructor's int param should be filled with a symbolic/nondet argument",
				result.source.contains("new TestClass(Verifier.nondetInt())"));
	}
}
