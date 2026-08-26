package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MultiTextEdit;
import org.junit.Test;

import transform.visitors.CommentPruningVisitor;

public class CommentPruningVisitorTest {

	private String applyPruning(String source) throws Exception {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		CommentPruningVisitor visitor = new CommentPruningVisitor(source);
		cu.accept(visitor);

		Document document = new Document(source);
		MultiTextEdit edits = visitor.getCommentsToDelete();
		edits.apply(document);
		return document.get();
	}

	@Test
	public void headerCommentBeforeClassIsRemoved() throws Exception {
		String source =
				"// license header\n" +
				"public class TestClass {\n" +
				"    void m() {}\n" +
				"}";

		String result = applyPruning(source);
		assertTrue("header comment should be removed", !result.contains("license header"));
	}

	@Test
	public void javadocAttachedToClassIsPreserved() throws Exception {
		// When a TypeDeclaration has a javadoc, firstBodyStart is anchored to
		// the javadoc's own start position, so the javadoc comment itself
		// never satisfies `start + length <= firstBodyStart` and is kept.
		// Only comments strictly before the javadoc get pruned.
		String source =
				"// stray header, not attached to the class\n" +
				"/**\n" +
				" * class javadoc\n" +
				" */\n" +
				"public class TestClass {\n" +
				"    void m() {}\n" +
				"}";

		String result = applyPruning(source);
		assertTrue("javadoc attached to the class should be kept", result.contains("class javadoc"));
		assertTrue("stray header preceding the javadoc should be removed",
				!result.contains("stray header"));
	}

	@Test
	public void commentInsideClassBodyIsNotRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    // inline comment\n" +
				"    void m() {}\n" +
				"}";

		String result = applyPruning(source);
		assertTrue("in-body comments should be left alone", result.contains("inline comment"));
	}

	@Test
	public void javadocOnMethodInsideClassIsNotRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    /** method javadoc */\n" +
				"    void m() {}\n" +
				"}";

		String result = applyPruning(source);
		assertTrue("method javadoc should be left alone", result.contains("method javadoc"));
	}

	@Test
	public void noCommentsMeansNothingToDelete() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void m() {}\n" +
				"}";

		assertEquals(source, applyPruning(source));
	}

	@Test
	public void trailingWhitespaceOnlyLineAfterHeaderCommentIsConsumed() throws Exception {
		String source =
				"// header\n" +
				"\n" +
				"public class TestClass {\n" +
				"}";

		String result = applyPruning(source);
		// The comment and the blank line it left behind should both be gone,
		// collapsing straight to the class declaration.
		assertEquals("public class TestClass {\n}", result);
	}
}
