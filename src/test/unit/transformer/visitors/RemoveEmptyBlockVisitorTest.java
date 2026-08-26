package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;

import transform.visitors.RemoveEmptyBlockVisitor;

public class RemoveEmptyBlockVisitorTest {

	private String applyRemoval(String source) throws Exception {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		Document document = new Document(source);
		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());

		cu.accept(new RemoveEmptyBlockVisitor(rewriter));

		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);
		return document.get().trim();
	}

	@Test
	public void emptyThenWithNonEmptyElseInvertsConditionAndDropsElse() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"        if (flag) {\n" +
				"        } else {\n" +
				"            System.out.println(\"else\");\n" +
				"        }\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"        if (!(flag)) {\n" +
				"			System.out.println(\"else\");\n" +
				"		}\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyRemoval(source));
	}

	@Test
	public void nonEmptyThenWithEmptyElseDropsElse() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"        if (flag) {\n" +
				"            System.out.println(\"then\");\n" +
				"        } else {\n" +
				"        }\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"        if (flag) {\n" +
				"            System.out.println(\"then\");\n" +
				"        }\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyRemoval(source));
	}

	@Test
	public void emptyThenAndEmptyElseRemovesWholeIfStatement() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"        if (flag) {\n" +
				"        } else {\n" +
				"        }\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyRemoval(source));
	}

	@Test
	public void emptyIfWithNoElseIsRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"        if (flag) {\n" +
				"        }\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void m(boolean flag) {\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyRemoval(source));
	}

	@Test
	public void emptyNonConstructorMethodIsRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void empty() {\n" +
				"    }\n" +
				"    void keep() {\n" +
				"        System.out.println(\"kept\");\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void keep() {\n" +
				"        System.out.println(\"kept\");\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyRemoval(source));
	}

	@Test
	public void emptyConstructorIsNotRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    public TestClass() {\n" +
				"    }\n" +
				"}";

		assertEquals(source.trim(), applyRemoval(source));
	}

	@Test
	public void nestedEmptyBlockInsideBlockIsRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        {\n" +
				"        }\n" +
				"        System.out.println(\"after\");\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void m() {\n" +
				"        System.out.println(\"after\");\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyRemoval(source));
	}

	@Test
	public void emptyWhileForEnhancedForDoAndSynchronizedAreRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m(int[] arr) {\n" +
				"        while (true) {\n" +
				"        }\n" +
				"        for (int i = 0; i < 10; i++) {\n" +
				"        }\n" +
				"        for (int x : arr) {\n" +
				"        }\n" +
				"        synchronized (this) {\n" +
				"        }\n" +
				"    }\n" +
				"}";

		String expected =
				"public class TestClass {\n" +
				"    void m(int[] arr) {\n" +
				"    }\n" +
				"}";

		assertEquals(expected.trim(), applyRemoval(source));
	}
}
