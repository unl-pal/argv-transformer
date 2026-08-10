package unit.sourceAnalysis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;

import sourceAnalysis.AnalyzedMethod;

public class AnalyzedMethodTest {

	private AnalyzedMethod method;

	@Before
	public void setup() {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		String source = "public class Sample { public void m(int a) {} }";
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		MethodDeclaration decl = (MethodDeclaration) ((TypeDeclaration) cu.types().get(0)).bodyDeclarations().get(0);
		method = new AnalyzedMethod(decl);
	}

	@Test
	public void isSymbolicSuitableRequiresAllThreeFlags() {
		assertFalse("no flags set yet", method.isSymbolicSuitable());

		method.setHasParameters(true);
		assertFalse("still missing type-only-params and type-operations", method.isSymbolicSuitable());

		method.setHasOnlyTypeParameters(true);
		assertFalse("still missing type-operations", method.isSymbolicSuitable());

		method.setHasTypeOperations(true);
		assertTrue("all three required flags are now set", method.isSymbolicSuitable());
	}

	@Test
	public void isSymbolicSuitableIsFalseWithoutParametersEvenIfOtherFlagsSet() {
		method.setHasOnlyTypeParameters(true);
		method.setHasTypeOperations(true);
		// hasParameters was never set to true.
		assertFalse(method.isSymbolicSuitable());
	}

	@Test
	public void nameIsTakenFromMethodDeclaration() {
		assertTrue(method.getName().equals("m"));
	}
}
