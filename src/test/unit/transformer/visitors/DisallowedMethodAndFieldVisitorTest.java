package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;

import transform.Transformer;
import transform.TypeChecking.TypeChecker;
import transform.visitors.DisallowedMethodAndFieldVisitor;

public class DisallowedMethodAndFieldVisitorTest {

	/**
	 * Parses with a resolvable environment (mirroring Transformer.getParser),
	 * rather than a bare ASTParser, so that resolveBinding() on locally
	 * declared methods returns real, distinguishable bindings instead of null.
	 * A bare parser makes every unresolved binding equal to `null`, which
	 * would make disallowedBindings.contains(binding) spuriously match
	 * unrelated methods after the first one is removed.
	 */
	private CompilationUnit parse(String source) {
		String[] classPath = {
				Paths.get("build", "classes", "java", "main").toString(),
				Paths.get("build", "classes", "java", "test").toString() };
		// Transformer.getParser hardcodes a 2-entry encodings array, so
		// sourcePath must also have exactly 2 entries or setEnvironment throws.
		String[] sourcePath = { Paths.get("src", "java").toString(), Paths.get("src", "java").toString() };
		ASTParser parser = Transformer.getParser(source, sourcePath, classPath, new File("TestClass.java"));
		return (CompilationUnit) parser.createAST(null);
	}

	private MethodDeclaration methodNamed(CompilationUnit cu, String name) {
		for (Object bodyDecl : ((TypeDeclaration) cu.types().get(0)).bodyDeclarations()) {
			if (bodyDecl instanceof MethodDeclaration && ((MethodDeclaration) bodyDecl).getName().getIdentifier().equals(name)) {
				return (MethodDeclaration) bodyDecl;
			}
		}
		throw new IllegalArgumentException("no method named " + name);
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
		CompilationUnit cu = parse(source);

		Document document = new Document(source);
		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());

		TypeChecker typeChecker = mock(TypeChecker.class);
		// Only the "int" return type is allowed; the "ExternalType" return type
		// falls through to `false`, which is the disallowed case under test.
		when(typeChecker.allowedType(org.mockito.Mockito.any(org.eclipse.jdt.core.dom.Type.class)))
				.thenAnswer(invocation -> invocation.<org.eclipse.jdt.core.dom.Type>getArgument(0)
						instanceof org.eclipse.jdt.core.dom.PrimitiveType);

		DisallowedMethodAndFieldVisitor visitor =
				new DisallowedMethodAndFieldVisitor(rewriter, Collections.emptySet(), typeChecker);
		cu.accept(visitor);

		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);

		String expected =
				"public class TestClass {\n" +
				"    int allowed() {\n" +
				"        return 1;\n" +
				"    }\n" +
				"}";
		assertEquals(expected.trim(), document.get().trim());
	}

	@Test
	public void constructorIsNeverRemovedRegardlessOfAllowedType() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    public TestClass() {\n" +
				"    }\n" +
				"}";
		CompilationUnit cu = parse(source);

		Document document = new Document(source);
		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());

		TypeChecker typeChecker = mock(TypeChecker.class);
		when(typeChecker.allowedType(org.mockito.Mockito.any(org.eclipse.jdt.core.dom.Type.class))).thenReturn(false);

		DisallowedMethodAndFieldVisitor visitor =
				new DisallowedMethodAndFieldVisitor(rewriter, Collections.emptySet(), typeChecker);
		cu.accept(visitor);

		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);

		assertEquals(source.trim(), document.get().trim());
	}

	@Test
	public void preDisallowedMethodAndItsCallSiteAreBothRemoved() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    void caller() {\n" +
				"        helper();\n" +
				"    }\n" +
				"    void helper() {\n" +
				"    }\n" +
				"}";
		CompilationUnit cu = parse(source);
		MethodDeclaration helper = methodNamed(cu, "helper");

		Document document = new Document(source);
		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());

		TypeChecker typeChecker = mock(TypeChecker.class);
		when(typeChecker.allowedType(org.mockito.Mockito.any(org.eclipse.jdt.core.dom.Type.class))).thenReturn(true);

		Set<ASTNode> disallowed = new HashSet<>();
		disallowed.add(helper);

		DisallowedMethodAndFieldVisitor visitor =
				new DisallowedMethodAndFieldVisitor(rewriter, disallowed, typeChecker);
		cu.accept(visitor);

		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);

		// Marking helper() as pre-disallowed removes both its own declaration
		// (visit(MethodDeclaration) checks disallowedBindings unconditionally)
		// and the call site in caller().
		String expected =
				"public class TestClass {\n" +
				"    void caller() {\n" +
				"    }\n" +
				"}";
		assertEquals(expected.trim(), document.get().trim());
	}

	/**
	 * Regression test for a null-binding contamination bug: when
	 * resolveBinding() can't resolve (e.g. no environment set on the parser),
	 * every unresolved method's binding is `null`. Once one such method is
	 * removed, its `null` binding used to get added to disallowedBindings,
	 * causing every other unresolved method to spuriously match
	 * disallowedBindings.contains(binding) and get removed too, regardless of
	 * whether it was actually disallowed.
	 */
	@Test
	public void unresolvedBindingOnOneMethodDoesNotContaminateOthers() throws Exception {
		String source =
				"public class TestClass {\n" +
				"    ExternalType disallowed() {\n" +
				"        return null;\n" +
				"    }\n" +
				"    void allowed() {\n" +
				"    }\n" +
				"}";

		// A bare parser with no environment: resolveBinding() returns null for
		// both locally declared methods here.
		ASTParser parser = ASTParser.newParser(org.eclipse.jdt.core.dom.AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		Document document = new Document(source);
		ASTRewrite rewriter = ASTRewrite.create(cu.getAST());

		TypeChecker typeChecker = mock(TypeChecker.class);
		when(typeChecker.allowedType(org.mockito.Mockito.any(org.eclipse.jdt.core.dom.Type.class)))
				.thenAnswer(invocation -> invocation.<org.eclipse.jdt.core.dom.Type>getArgument(0).isPrimitiveType());

		DisallowedMethodAndFieldVisitor visitor =
				new DisallowedMethodAndFieldVisitor(rewriter, Collections.emptySet(), typeChecker);
		cu.accept(visitor);

		TextEdit edits = rewriter.rewriteAST(document, null);
		edits.apply(document);

		// void is a primitive type, so allowed() should survive; only
		// disallowed() (ExternalType return) should be removed.
		String expected =
				"public class TestClass {\n" +
				"    void allowed() {\n" +
				"    }\n" +
				"}";
		assertEquals(expected.trim(), document.get().trim());
	}
}
