package test.transformer.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.Before;
import org.junit.Test;

import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeTable;
import transform.visitors.TransformVisitor;

public class TransformVisitorTest {
	
	private TransformVisitor visitor;
	
	private ASTParser parser;
    private AST ast;
	
	@Before
	public void setup() {
		parser = ASTParser.newParser(AST.JLS8); // Use appropriate JLS version
        ast = AST.newAST(AST.JLS8);
		visitor = new TransformVisitor(null, null, null, null, null);
	}
	
	@Test
	public void test_visitCompilationUnit_correctlyRemovesImports() throws Exception {
		// Sample source with mixed imports
        String source = 
			    "import java.util.List;\n" +
			    "import my.pkg.CustomClass;\n" +
			    "import another.pkg.AnotherClass;\n";

        // Parse the source code
        parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        // Create a rewriter and a document
        Document document = new Document(source);
        ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

        // Instantiate the visitor and set the rewriter
        visitor = new TransformVisitor(null, rewriter, null, null, null);
        visitor.visit(compilationUnit);

        // Apply the changes made by the rewriter
        TextEdit edits = rewriter.rewriteAST(document, null);
        edits.apply(document);

        // Check the updated source code
        String expectedSource = 
            "import java.util.List;\n";
        assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitCompilationUnit_correctlyAddsNewImport() throws Exception {
		// Sample source with mixed imports
        String source = 
        		"import java.util.List;\n";

        // Parse the source code
        parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        // Create a rewriter and a document
        Document document = new Document(source);
        ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

        // Instantiate the visitor and set the rewriter/target
        visitor = new TransformVisitor(null, rewriter, null, null, "SPF");
        visitor.visit(compilationUnit); // sets ast
        visitor.endVisit(compilationUnit);

        // Apply the changes made by the rewriter
        TextEdit edits = rewriter.rewriteAST(document, null);
        edits.apply(document);

        // Check the updated source code
        String expectedSource = 
        		"import gov.nasa.jpf.symbc.Debug;\n" 
        		+ "import java.util.List;\n";
        assertEquals(expectedSource.trim(), document.get().trim());
	}

	@Test
	public void test_visitMethodDeclaration_removesDisallowedTypes() throws Exception {
		// Example source code with a method containing a disallowed parameter type
        String source =
            "public class TestClass {\n" +
            "    public void allowedMethod(int a) {}\n" +
            "    public void disallowedMethod(Object obj) {}\n" +
            "}";

        // Parse the source code
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        // Create a document and an ASTRewrite instance
        Document document = new Document(source);
        ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

        SymbolTable root = new SymbolTable(null);
        root.put("allowedMethodi", new MethodSTE("allowedMethodi")); // configuring methodSTE to work with system
        visitor = new TransformVisitor(root, rewriter, null, null, null);
        
        List<MethodDeclaration> methodDeclarations = ((TypeDeclaration)compilationUnit.types().get(0)).bodyDeclarations();
        visitor.visit(compilationUnit);
        for (MethodDeclaration declaration : methodDeclarations) {
        	visitor.visit(declaration);
        }

        // Apply the changes made by the visitor
        TextEdit edits = rewriter.rewriteAST(document, null);
        edits.apply(document);

        // Verify the updated source code
        String expectedSource =
            "public class TestClass {\n" +
            "    public void allowedMethod(int a) {}\n" +
            "}";
        assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitMethodInvocation_replaces() throws Exception {
		// Example source code with a method containing a disallowed parameter type
		 // Example source code
        String source = 
            "public class TestClass {\n" +
            "    public void testMethod() {\n" +
            "        int result = ExternalClass.externalMethod();\n" +
            "    }\n" +
            "}";

        // Parse the source code
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

        // Set up the document for rewriting
        Document document = new Document(source);
        ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

        // Mock behavior for replaceInteger
        MethodInvocation methodInvocation = (MethodInvocation)
        		((VariableDeclarationFragment)((VariableDeclarationStatement)((MethodDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0))
        		.getBody().statements().get(0)).fragments().get(0)).getInitializer();
        // TODO: use mocking to resolve method binding
        IMethodBinding mockBinding = mock(IMethodBinding.class);
        ITypeBinding mockReturnType = mock(ITypeBinding.class);
        ITypeBinding mockDeclaringClass = mock(ITypeBinding.class);
        IPackageBinding mockPackage = mock(IPackageBinding.class);

        when(mockBinding.getName()).thenReturn("externalMethod");
        when(mockBinding.getReturnType()).thenReturn(mockReturnType);
        when(mockReturnType.isPrimitive()).thenReturn(true);
        when(mockReturnType.getName()).thenReturn("int");
        when(mockBinding.getDeclaringClass()).thenReturn(mockDeclaringClass);
        when(mockDeclaringClass.getPackage()).thenReturn(mockPackage);
        when(mockPackage.getName()).thenReturn("external.pkg");

        visitor = new TransformVisitor(null, rewriter, null, null, "SPF");
        visitor.endVisit(methodInvocation);

        // Apply the rewrites to the document
        rewriter.rewriteAST(document, null).apply(document);
        
        // Verify the output contains the replacement
        String expectedOutput = 
            "public class TestClass {\n" +
            "    public void testMethod() {\n" +
            "        int result = Debug.MakeSymbolicInt(\"x0\");\n" +
            "    }\n" +
            "}";
        assertEquals(expectedOutput.trim(), document.get().trim());
	}
	
	
}
