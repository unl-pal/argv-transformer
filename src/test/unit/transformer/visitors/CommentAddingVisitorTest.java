package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;

import transform.visitors.CommentAddingVisitor;

public class CommentAddingVisitorTest {

	/**
	 * Runs the visitor and normalizes whitespace (matching the convention
	 * DirectoryDiffTest uses), since ASTRewrite inserts incidental blank lines
	 * around string-placeholder comments that aren't part of the behavior
	 * under test here (comment order/content, not exact formatting).
	 */
	private String applyCommentAdding(String source, List<String> preImportComments, List<String> postImportComments) throws Exception {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		Document document = new Document(source);
		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());

		cu.accept(new CommentAddingVisitor(rewriter, preImportComments, postImportComments));

		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);
		return document.get().trim().replaceAll("\\s+", " ");
	}

	private String normalize(String s) {
		return s.trim().replaceAll("\\s+", " ");
	}

	@Test
	public void disclaimerIsAlwaysInsertedAboveImports() throws Exception {
		String source = "import java.util.List;\npublic class TestClass {}";

		String result = applyCommentAdding(source, new ArrayList<>(), new ArrayList<>());

		assertEquals(normalize(
				"/** [ARG-V](https://arg-v.dev) was used to collect, filter, and transform these benchmarks automatically */\n" +
				"import java.util.List;\npublic class TestClass {}"),
				result);
	}

	@Test
	public void preImportCommentsAreInsertedAboveImportsBelowDisclaimerInOriginalOrder() throws Exception {
		String source = "import java.util.List;\npublic class TestClass {}";
		List<String> preImportComments = new ArrayList<>();
		preImportComments.add("// first\n");
		preImportComments.add("// second\n");

		String result = applyCommentAdding(source, preImportComments, new ArrayList<>());

		// The disclaimer always ends up topmost; the caller's own pre-import
		// comments are inserted below it, in their original order.
		assertEquals(normalize(
				"/** [ARG-V](https://arg-v.dev) was used to collect, filter, and transform these benchmarks automatically */\n" +
				"// first\n" +
				"// second\n" +
				"import java.util.List;\npublic class TestClass {}"),
				result);
	}

	@Test
	public void postImportCommentsAreInsertedBelowImportsInOriginalOrder() throws Exception {
		String source = "import java.util.List;\npublic class TestClass {}";
		List<String> postImportComments = new ArrayList<>();
		postImportComments.add("// first\n");
		postImportComments.add("// second\n");

		String result = applyCommentAdding(source, new ArrayList<>(), postImportComments);

		assertEquals(normalize(
				"/** [ARG-V](https://arg-v.dev) was used to collect, filter, and transform these benchmarks automatically */\n" +
				"import java.util.List;\n" +
				"// first\n" +
				"// second\n" +
				"public class TestClass {}"),
				result);
	}

	@Test
	public void preAndPostImportCommentsCanBothBePresent() throws Exception {
		String source = "import java.util.List;\npublic class TestClass {}";
		List<String> preImportComments = new ArrayList<>();
		preImportComments.add("// pre\n");
		List<String> postImportComments = new ArrayList<>();
		postImportComments.add("// post\n");

		String result = applyCommentAdding(source, preImportComments, postImportComments);

		assertEquals(normalize(
				"/** [ARG-V](https://arg-v.dev) was used to collect, filter, and transform these benchmarks automatically */\n" +
				"// pre\n" +
				"import java.util.List;\n" +
				"// post\n" +
				"public class TestClass {}"),
				result);
	}
}
