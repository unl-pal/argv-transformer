package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Paths;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;

import transform.Transformer;
import transform.visitors.PreprocessingVisitor;

public class PreprocessingVisitorTest {

	/**
	 * Parses with a resolvable environment (mirroring Transformer.getParser) so
	 * that resolveBinding() on locally declared methods/params returns real
	 * bindings instead of null. PreprocessingVisitor's method visitor
	 * unconditionally checks allowedType() on every method's return/param
	 * bindings, so a bare (unresolved) parser would remove every method here
	 * regardless of what's under test.
	 */
	private String applyPreprocessing(String source) throws Exception {
		String[] classPath = {
				Paths.get("build", "classes", "java", "main").toString(),
				Paths.get("build", "classes", "java", "test").toString() };
		String[] sourcePath = { Paths.get("src", "java").toString(), Paths.get("src", "java").toString() };
		ASTParser parser = Transformer.getParser(source, sourcePath, classPath, new File("TestClass.java"));
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		Document document = new Document(source);
		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());

		cu.accept(new PreprocessingVisitor(rewriter, cu.getAST()));

		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);
		return document.get().trim();
	}

	@Test
	public void anonymousClassDeclarationIsRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    Runnable r = new Runnable() {\n" +
				"        public void run() {}\n" +
				"    };\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    Runnable r = new Runnable();\n" +
				"}";

		assertEquals(expected.trim(), applyPreprocessing(source));
	}

	@Test
	public void oneLineIfStatementIsWrappedInBlock() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m(int x) {\n" +
				"        if (x > 0)\n" +
				"            System.out.println(x);\n" +
				"    }\n" +
				"}";

		String result = applyPreprocessing(source);
		assertEquals(
				"public class TestClass {\n" +
				"    void m(int x) {\n" +
				"        if (x > 0) {\n" +
				"		\tSystem.out.println(x);\n" +
				"	\t}\n" +
				"    }\n" +
				"}".trim(),
				result);
	}

	@Test
	public void alreadyBlockIfStatementIsUnchanged() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m(int x) {\n" +
				"        if (x > 0) {\n" +
				"            System.out.println(x);\n" +
				"        }\n" +
				"    }\n" +
				"}";

		assertEquals(source.trim(), applyPreprocessing(source));
	}

	@Test
	public void nestedTypeDeclarationIsRemoved() throws Exception {
		String source =
				"public class Outer {\n" +
				"    class Inner {\n" +
				"        void m() {}\n" +
				"    }\n" +
				"    void keep() {}\n" +
				"}";

		String expected =
				"public class Outer {\n" +
				"    void keep() {}\n" +
				"}";

		assertEquals(expected.trim(), applyPreprocessing(source));
	}

	@Test
	public void topLevelTypeDeclarationIsNotRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m() {}\n" +
				"}";

		assertEquals(source.trim(), applyPreprocessing(source));
	}

	@Test
	public void methodWithDisallowedReturnTypeIsRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    ExternalType disallowed() {\n" +
				"        return null;\n" +
				"    }\n" +
				"    int allowed() {\n" +
				"        return 1;\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    int allowed() {\n" +
				"        return 1;\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyPreprocessing(source));
	}

	@Test
	public void methodWithDisallowedParameterTypeIsRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void disallowed(ExternalType p) {\n" +
				"    }\n" +
				"    void allowed(int p) {\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void allowed(int p) {\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyPreprocessing(source));
	}

	@Test
	public void constructorIsNeverRemovedRegardlessOfReturnTypeCheck() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    public TestClass(int x) {\n" +
				"    }\n" +
				"}";

		assertEquals(source.trim(), applyPreprocessing(source));
	}
}
