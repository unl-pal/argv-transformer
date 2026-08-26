package unit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import util.AnalyzedFile;
import util.AnalyzedMethod;

public class AnalyzedFileTest {

	private MethodDeclaration parseFirstMethod(String source) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		return (MethodDeclaration) ((TypeDeclaration) cu.types().get(0)).bodyDeclarations().get(0);
	}

	@Test
	public void newFileHasNoAnalyzedOrSuitableMethods() {
		AnalyzedFile af = new AnalyzedFile(new File("Sample.java"));
		assertTrue(af.getAnalyzedMethods().isEmpty());
		assertTrue(af.getSuitableMethods().isEmpty());
		assertFalse(af.isSuitable());
	}

	@Test
	public void isSuitableReflectsOnlySuitableMethodsSet() {
		AnalyzedFile af = new AnalyzedFile(new File("Sample.java"));
		MethodDeclaration decl = parseFirstMethod(
				"public class Sample { public void m() {} }");
		AnalyzedMethod method = new AnalyzedMethod(decl);

		af.addMethod(method);
		// Added to analyzedMethods, but not yet marked suitable.
		assertFalse(af.isSuitable());

		af.addSuitableMethod(method);
		assertTrue(af.isSuitable());
	}

	@Test
	public void pathIsDerivedFromAbsoluteFilePath() {
		File file = new File("Sample.java");
		AnalyzedFile af = new AnalyzedFile(file);
		assertEquals(file.getAbsolutePath(), af.getPath());
		assertEquals(file, af.getFile());
	}
}
