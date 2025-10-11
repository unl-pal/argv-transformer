package unit.transformer.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;
import transform.TypeChecking.TypeChecker;
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
		visitor = new TransformVisitor(null, null, null, null, null, null);
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
        visitor = new TransformVisitor(null, rewriter, null, null, "SVCOMP", source);
        visitor.visit(compilationUnit);
        visitor.endVisit(compilationUnit);

        // Apply the changes made by the rewriter
        TextEdit edits = rewriter.rewriteAST(document, null);
        edits.apply(document);

        // Check the updated source code
        String expectedSource = 
        	"import org.sosy_lab.sv_benchmarks.Verifier;\n" +
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
        visitor = new TransformVisitor(null, rewriter, null, null, "SVCOMP", source);
        visitor.visit(compilationUnit); // sets ast
        visitor.endVisit(compilationUnit);

        // Apply the changes made by the rewriter
        TextEdit edits = rewriter.rewriteAST(document, null);
        edits.apply(document);

        // Check the updated source code
        String expectedSource = 
        		"import org.sosy_lab.sv_benchmarks.Verifier;\n" 
        		+ "import java.util.List;\n";
        assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitMethodInvocation_replaces() throws Exception {
		// Example source code with a method containing a disallowed parameter type
        String source = 
        	"package internal.pkg;\n" +
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
      
        IMethodBinding mockBinding = mock(IMethodBinding.class);
        ITypeBinding mockReturnType = mock(ITypeBinding.class);
        ITypeBinding mockDeclaringClass = mock(ITypeBinding.class);
        IPackageBinding mockPackage = mock(IPackageBinding.class);   
        
        methodInvocation = Mockito.spy(methodInvocation);
        when(methodInvocation.resolveMethodBinding()).thenReturn(mockBinding);
        
        when(mockBinding.getName()).thenReturn("externalMethod");
        when(mockBinding.getReturnType()).thenReturn(mockReturnType);
        when(mockReturnType.isPrimitive()).thenReturn(true);
        when(mockReturnType.getName()).thenReturn("int");
        when(mockBinding.getDeclaringClass()).thenReturn(mockDeclaringClass);
        when(mockDeclaringClass.getPackage()).thenReturn(mockPackage);
        when(mockPackage.getName()).thenReturn("external.pkg");

        visitor = new TransformVisitor(null, rewriter, null, null, "SVCOMP", source);
        visitor.visit(compilationUnit);
        visitor.endVisit(methodInvocation);
	    TransformVisitor.varNum = 0;

        // Apply the rewrites to the document
        rewriter.rewriteAST(document, null).apply(document);
        
        // Verify the output contains the replacement
        String expectedOutput = 
        	"package internal.pkg;\n" +
            "public class TestClass {\n" +
            "    public void testMethod() {\n" +
            "        int result = Verifier.nondetInt();\n" +
            "    }\n" +
            "}";
        assertEquals(expectedOutput.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitNormalAnnotation_removesAnnotation() throws Exception {
	    // Example source code with a normal annotation
	    String source = 
	        "@TestAnnotation(key1 = value1)\n" +
	        "public class TestClass {\n" +
	        "    public void someMethod() {}\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    NormalAnnotation normalAnnotation = (NormalAnnotation)((TypeDeclaration)compilationUnit.types().get(0)).modifiers().get(0);

	    visitor = new TransformVisitor(null, rewriter, null, null, "SVCOMP", source);
	    visitor.visit(normalAnnotation);
	    // Apply the changes made by the rewriter
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {}\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitSimpleName_replacesDisallowedTypeInIfExpression() throws Exception {
	    // Example source code
	    String source = 
	        "public class TestClass {\n" +
	        "    public void testMethod() {\n" +
	        "        if (someVariable) {\n" +
	        "            // Do something\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock the TypeTable
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    when(mockTypeTable.getNodeType(Mockito.any(SimpleName.class))).thenReturn(null);
	    
	    SimpleName simpleName = (SimpleName)((IfStatement)((MethodDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0))
        		.getBody().statements().get(0)).getExpression();
	    
	    // Create the visitor
	    TypeChecker typeChecker = new TypeChecker();
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, typeChecker, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(simpleName);
	    TransformVisitor.varNum = 0;
	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the changes
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void testMethod() {\n" +
	        "        if (Verifier.nondetBoolean()) {\n" +
	        "            // Do something\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitPrefixExpression_replacesDisallowedTypeInIfExpression() throws Exception {
	    // Example source code
	    String source = 
	        "public class TestClass {\n" +
	        "    public void testMethod() {\n" +
	        "        if (!someVariable) {\n" +
	        "            // Do something\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock the TypeTable
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    when(mockTypeTable.getNodeType(Mockito.any(PrefixExpression.class))).thenReturn(null);

	    PrefixExpression prefixExpression = (PrefixExpression)((IfStatement)((MethodDeclaration)((TypeDeclaration)
	            compilationUnit.types().get(0)).bodyDeclarations().get(0))
	            .getBody().statements().get(0)).getExpression();
	    
	    // Create the visitor
	    TypeChecker typeChecker = new TypeChecker();
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, typeChecker, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(prefixExpression);
	    TransformVisitor.varNum = 0;

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the changes
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void testMethod() {\n" +
	        "        if (Verifier.nondetBoolean()) {\n" +
	        "            // Do something\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitPrefixExpression_replacesDisallowedTypeInWhileExpression() throws Exception {
	    // Example source code
	    String source = 
	        "public class TestClass {\n" +
	        "    public void testMethod() {\n" +
	        "        while (!someVariable) {\n" +
	        "            // Do something\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock the TypeTable
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    when(mockTypeTable.getNodeType(Mockito.any(PrefixExpression.class))).thenReturn(null);

	    PrefixExpression prefixExpression = (PrefixExpression)((WhileStatement)((MethodDeclaration)((TypeDeclaration)
	            compilationUnit.types().get(0)).bodyDeclarations().get(0))
	            .getBody().statements().get(0)).getExpression();
	    
	    // Create the visitor
	    TypeChecker typeChecker = new TypeChecker();
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, typeChecker, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(prefixExpression);
	    TransformVisitor.varNum = 0;

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the changes
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void testMethod() {\n" +
	        "        while (Verifier.nondetBoolean()) {\n" +
	        "            // Do something\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitReturnStatement_replacesDisallowedReturnType() throws Exception {
	    // Example source code
	    String source = 
	        "public class TestClass {\n" +
	        "    public String testMethod() {\n" +
	        "        return someString;\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock SymbolTableStack and TypeTable
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    SymbolTable mockSymbolTable = mock(SymbolTable.class);
	    TypeChecker mockTypeChecker = mock(TypeChecker.class);
	    MethodSTE mockMethodSTE = mock(MethodSTE.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);

	    // Mock the current method and return type
	    when(mockMethodSTE.getReturnType()).thenReturn(mockType);
	    when(mockTypeChecker.allowedType(mockType)).thenReturn(true);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);

	    // Mock typeTable to return null for the expression type
	    when(mockSymbolTable.getMethodSTE(Mockito.any())).thenReturn(mockMethodSTE);
	    when(mockTypeTable.getNodeType(Mockito.any(Expression.class))).thenReturn(null);

	    // Get the ReturnStatement node
	    ReturnStatement returnStatement = (ReturnStatement)((MethodDeclaration)((TypeDeclaration)
	            compilationUnit.types().get(0)).bodyDeclarations().get(0))
	            .getBody().statements().get(0);

	    // Create the visitor
	    TransformVisitor visitor = new TransformVisitor(mockSymbolTable, rewriter, mockTypeTable, mockTypeChecker, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(returnStatement);
	    TransformVisitor.varNum = 0;

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the changes
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public String testMethod() {\n" +
	        "        assert true; //inline assert generated by ARG-V\n" +
	        "		return null;\n" +
	        "    }\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitReturnStatement_replacesExternalReturnType() throws Exception {
	    // Example source code
	    String source = 
	        "public class TestClass {\n" +
	        "    public externalType testMethod() {\n" +
	        "        return someType;\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock SymbolTableStack and TypeTable
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    SymbolTable mockSymbolTable = mock(SymbolTable.class);
	    TypeChecker mockTypeChecker = mock(TypeChecker.class);
	    MethodSTE mockMethodSTE = mock(MethodSTE.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);

	    // Mock the current method and return type
	    when(mockMethodSTE.getReturnType()).thenReturn(mockType);
	    when(mockTypeChecker.allowedType(mockType)).thenReturn(false);

	    // Mock typeTable to return null for the expression type
	    when(mockSymbolTable.getMethodSTE(Mockito.any())).thenReturn(mockMethodSTE);
	    when(mockTypeTable.getNodeType(Mockito.any(Expression.class))).thenReturn(null);

	    // Get the ReturnStatement node
	    ReturnStatement returnStatement = (ReturnStatement)((MethodDeclaration)((TypeDeclaration)
	            compilationUnit.types().get(0)).bodyDeclarations().get(0))
	            .getBody().statements().get(0);

	    // Create the visitor
	    TransformVisitor visitor = new TransformVisitor(mockSymbolTable, rewriter, mockTypeTable, mockTypeChecker, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(returnStatement);
	    TransformVisitor.varNum = 0;

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the changes
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public externalType testMethod() {\n" +
	        "        assert true; //inline assert generated by ARG-V\n" +
	        "		return new Object();\n" +
	        "    }\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitSingleMemberAnnotation_removesAnnotation() throws Exception {
	    // Example source code with a single-member annotation
	    String source = 
	        "@TestAnnotation(\"value\")\n" +
	        "public class TestClass {\n" +
	        "    public void someMethod() {}\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Get the SingleMemberAnnotation node
	    SingleMemberAnnotation singleMemberAnnotation = (SingleMemberAnnotation) 
	        ((TypeDeclaration) compilationUnit.types().get(0)).modifiers().get(0);

	    // Visit the annotation node
	    visitor = new TransformVisitor(null, rewriter, null, null, "SVCOMP", source);
	    visitor.visit(singleMemberAnnotation);

	    // Apply the changes made by the rewriter
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {}\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitSuperConstructorInvocation_removesInvocation() throws Exception {
	    // Example source code with a super constructor invocation
	    String source = 
	        "public class ChildClass extends ParentClass {\n" +
	        "    public ChildClass() {\n" +
	        "        super();\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Get the SuperConstructorInvocation node
	    SuperConstructorInvocation superConstructorInvocation = 
	        (SuperConstructorInvocation) (((TypeDeclaration) compilationUnit.types().get(0)).getMethods()[0]).getBody().statements().get(0);

	    // Visit the node
	    visitor = new TransformVisitor(null, rewriter, null, null, "SVCOMP", source);
	    visitor.visit(superConstructorInvocation);

	    // Apply the changes made by the rewriter
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class ChildClass extends ParentClass {\n" +
	        "    public ChildClass() {\n" +
	        "    }\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitVariableDeclarationStatement_removesDisallowedTypesInBlock() throws Exception {
	    // Example source code with variable declarations
	    String source =
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        ExternalType obj = new ExternalType();\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock the TypeChecker to disallow certain types
	    TypeChecker typeChecker = Mockito.mock(TypeChecker.class);
	    when(typeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);

	    // Retrieve the variable declaration statements
	    VariableDeclarationStatement varDecl = (VariableDeclarationStatement) 
	    		(((Block) ((MethodDeclaration) ((TypeDeclaration) 
	    		compilationUnit.types().get(0)).getMethods()[0]).getBody()).statements()).get(0);

	    // Visit the nodes
	    visitor = new TransformVisitor(null, rewriter, null, typeChecker, null, source);
	    visitor.visit(varDecl);

	    // Apply the changes made by the rewriter
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource =
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "    }\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitVariableDeclarationStatement_placesEmptyBlockInNonBlock() throws Exception {
	    // Example source code with variable declarations
		String source = 
		        "public class TestClass {\n" +
		        "    public void someMethod(int x) {\n" +
		        "        switch (x) {\n" +
		        "            case 1:\n" +
		        "                ExternalType obj = new ExternalType();\n" +
		        "                break;\n" +
		        "            default:\n" +
		        "                break;\n" +
		        "        }\n" +
		        "    }\n" +
		        "}";
	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock the TypeChecker to disallow certain types
	    TypeChecker typeChecker = Mockito.mock(TypeChecker.class);
	    when(typeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);

	    // Retrieve the variable declaration statements

	    MethodDeclaration method = 
	            (MethodDeclaration) ((TypeDeclaration) compilationUnit.types().get(0)).getMethods()[0];
	    SwitchStatement switchStatement = 
            (SwitchStatement) method.getBody().statements().get(0);
	    VariableDeclarationStatement varDecl = 
            (VariableDeclarationStatement) switchStatement.statements().get(1); // 0th case is "case 0:"
        
	    // Visit the nodes
	    visitor = new TransformVisitor(null, rewriter, null, typeChecker, null, source);
	    visitor.visit(compilationUnit);
	    visitor.visit(varDecl);

	    // Apply the changes made by the rewriter
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource =
	    		"public class TestClass {\n" + 
	    		"    public void someMethod(int x) {\n" + 
	    		"        switch (x) {\n" + 
	    		"            case 1:\n" + 
	    		"			{\n" + // must be tabs
	    		"			}\n" + 
	    		"                break;\n" + 
	    		"            default:\n" + 
	    		"                break;\n" + 
	    		"        }\n" + 
	    		"    }\n" + 
	    		"}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	

	@Test
	public void test_visitTypeDeclaration_removesInterfaceAndSuperclass() throws Exception {
	    // Example source code with a class and an interface
	    String source = 
	        "public class TestClass extends SuperClass implements Interface1, Interface2 {\n" +
	        "    public void someMethod() {}\n" +
	        "}";
	
	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
	
	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());
	
	    // Set up mock behavior
	    SymbolTable mockRoot = mock(SymbolTable.class);
	    SymbolTable mockClassScope = mock(SymbolTable.class);
	    ClassSTE mockClassSTE = mock(ClassSTE.class);
	    when(mockRoot.getClassSTE("TestClass")).thenReturn(mockClassSTE);
	    when(mockClassSTE.getSymbolTable()).thenReturn(mockClassScope);
	
	    TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);

	    TransformVisitor visitor = new TransformVisitor(mockRoot, rewriter, null, null, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.visit(typeDeclaration);
	
	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);
	
	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {}\n" +
	        "}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitTypeDeclaration_popsSymbolTableForNonInterface() throws Exception {
	    // Example source code with a class and an interface
	    String source = 
	        "public class TestClass{\n" +
	        "    public void someMethod() {}\n" +
	        "}";
	
	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
	
	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());
	
	    // Set up mock behavior
	    SymbolTable mockRoot = mock(SymbolTable.class);	
	    TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);

	    TransformVisitor visitor = new TransformVisitor(mockRoot, rewriter, null, null, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(typeDeclaration);
	    
	    assertFalse(visitor.getSymbolTableStack().contains(mockRoot));
	}
	
	@Test
	public void test_endVisitTypeDeclaration_keepsSymbolTableForInterface() throws Exception {
	    // Example source code with a class and an interface
	    String source = 
	        "public interface TestClass{\n" +
	        "    public void someMethod();\n" +
	        "}";
	
	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
	
	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());
	
	    // Set up mock behavior
	    SymbolTable mockRoot = mock(SymbolTable.class);	
	    TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);

	    TransformVisitor visitor = new TransformVisitor(mockRoot, rewriter, null, null, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(typeDeclaration);
	    
	    assertTrue(visitor.getSymbolTableStack().contains(mockRoot));
	}
	
	@Test
	public void test_endVisitClassInstanceCreation_removesDisallowedTypesInVariableAssignment() throws Exception {
	    // Example source code to test disallowed types handling in ClassInstanceCreation
	    String source =
	        "public class TestClass {\n" +
	        "    public void method() {\n" +
	        "        Object obj = new DisallowedType();\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    AST ast = compilationUnit.getAST();
	    ASTRewrite rewriter = ASTRewrite.create(ast);

	    // Mock the type checker and type table using Mockito
	    TypeChecker typeChecker = Mockito.mock(TypeChecker.class);
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    when(typeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);
	    
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, typeChecker, null, source);
	    ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation) 
	      		((VariableDeclarationFragment)((VariableDeclarationStatement)((MethodDeclaration)((TypeDeclaration)
	            		compilationUnit.types().get(0)).bodyDeclarations().get(0))
	            		.getBody().statements().get(0)).fragments().get(0)).getInitializer();

	    // Visit the nodes in the AST
	    visitor.visit(compilationUnit);
	    visitor.endVisit(classInstanceCreation);
	    
	    
	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource =
	        "public class TestClass {\n" +
	        "    public void method() {\n" +
	        "    }\n" +
	        "}";

	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	@Test
	public void test_endVisitClassInstanceCreation_replacesDisallowedTypeInReturnStatement() throws Exception {
	    // Example source code to test disallowed types handling in ClassInstanceCreation
	    String source =
	        "public class TestClass {\n" +
	        "    public DisallowedType method() {\n" +
	        "        return new DisallowedType();\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    AST ast = compilationUnit.getAST();
	    ASTRewrite rewriter = ASTRewrite.create(ast);

	    // Mock the type checker and type table using Mockito
	    TypeChecker typeChecker = Mockito.mock(TypeChecker.class);
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    when(typeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);
	    
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, typeChecker, null, source);
	    ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation) 
	    	    ((ReturnStatement)((MethodDeclaration)((TypeDeclaration)
	    	            compilationUnit.types().get(0)).bodyDeclarations().get(0))
	    	            .getBody().statements().get(0)).getExpression();

	    // Visit the nodes in the AST
	    visitor.visit(compilationUnit);
	    visitor.endVisit(classInstanceCreation);
	    
	    
	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource =
	        "public class TestClass {\n" +
	        "    public DisallowedType method() {\n" +
	        "        return null;\n"+
	        "    }\n" +
	        "}";

	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitConditionalExpression_replacesDisallowedIntegerType() throws Exception {
	    // Example source code with variable declarations
		String source = 
		        "public class TestClass {\n" +
		        "    public void someMethod(int x) {\n" +
		        "        int x = 3 > 5 ? ExternalClass.disallowedMethod() : ExternalClass.disallowedMethod();\n" +
		        "    }\n" +
		        "}";
	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock the TypeChecker to disallow certain types
	    TypeChecker typeChecker = Mockito.mock(TypeChecker.class);
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);
	    when(mockTypeTable.getNodeType(Mockito.any(VariableDeclarationFragment.class))).thenReturn(mockType);
	    when(typeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);


	    // Retrieve the variable declaration statements

	    ConditionalExpression conditionalExpression = (ConditionalExpression)
	    		((VariableDeclarationFragment)((VariableDeclarationStatement)((MethodDeclaration)((TypeDeclaration)
	            		compilationUnit.types().get(0)).bodyDeclarations().get(0))
	            		.getBody().statements().get(0)).fragments().get(0)).getInitializer();
        
	    // Visit the nodes
	    visitor = new TransformVisitor(null, rewriter, mockTypeTable, typeChecker, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(conditionalExpression);

	    // Apply the changes made by the rewriter
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource =
	    		"public class TestClass {\n" + 
	    		"    public void someMethod(int x) {\n" + 
	    		"        int x = Verifier.nondetBoolean() ? Verifier.nondetInt() : Verifier.nondetInt();\n" +
	    		"    }\n" + 
	    		"}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitConditionalExpression_replacesDisallowedDoubleType() throws Exception {
	    // Example source code with variable declarations
		String source = 
		        "public class TestClass {\n" +
		        "    public void someMethod(int x) {\n" +
		        "        double x = 3 > 5 ? ExternalClass.disallowedMethod() : ExternalClass.disallowedMethod();\n" +
		        "    }\n" +
		        "}";
	    // Parse the source code
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);

	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Mock the TypeChecker to disallow certain types
	    TypeChecker typeChecker = Mockito.mock(TypeChecker.class);
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);
	    when(mockTypeTable.getNodeType(Mockito.any(VariableDeclarationFragment.class))).thenReturn(mockType);
	    when(typeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.DOUBLE);


	    // Retrieve the variable declaration statements

	    ConditionalExpression conditionalExpression = (ConditionalExpression)
	    		((VariableDeclarationFragment)((VariableDeclarationStatement)((MethodDeclaration)((TypeDeclaration)
	            		compilationUnit.types().get(0)).bodyDeclarations().get(0))
	            		.getBody().statements().get(0)).fragments().get(0)).getInitializer();
        
	    // Visit the nodes
	    visitor = new TransformVisitor(null, rewriter, mockTypeTable, typeChecker, "SVCOMP", source);
	    visitor.visit(compilationUnit);
	    visitor.endVisit(conditionalExpression);

	    // Apply the changes made by the rewriter
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource =
	    		"public class TestClass {\n" + 
	    		"    public void someMethod(int x) {\n" + 
	    		"        double x = Verifier.nondetBoolean() ? Verifier.nondetDouble() : Verifier.nondetDouble();\n" +
	    		"    }\n" + 
	    		"}";
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitSuperMethodInvocation_replacesInVariableDeclaration() throws Exception {
	    // Example source code containing a SuperMethodInvocation in a variable assignment
	    String source = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        int x = super.hashCode();\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Set up mock behavior
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);

	    // Mock the current method and return type
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(mockType);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);

	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, null, "SVCOMP", source);
	    SuperMethodInvocation superMethodInvocation = (SuperMethodInvocation)
	        		((VariableDeclarationFragment)((VariableDeclarationStatement)((MethodDeclaration)((TypeDeclaration)
	        		compilationUnit.types().get(0)).bodyDeclarations().get(0))
	        		.getBody().statements().get(0)).fragments().get(0)).getInitializer();
	    visitor.visit(compilationUnit);
	    visitor.endVisit(superMethodInvocation);
	    

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        int x = Verifier.nondetInt();\n" +
	        "    }\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitSuperMethodInvocation_replacesInVariableAssignment() throws Exception {
	    // Example source code containing a SuperMethodInvocation in a variable assignment
	    String source = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        int x;\n" +
	        "        x = super.hashCode();\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Set up mock behavior
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);

	    // Mock the current method and return type
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(mockType);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);

	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, null, "SVCOMP", source);
	    SuperMethodInvocation superMethodInvocation = (SuperMethodInvocation)
	        		 ((Assignment)(((ExpressionStatement)((MethodDeclaration)((TypeDeclaration)
		compilationUnit.types().get(0)).bodyDeclarations().get(0))
		.getBody().statements().get(1)).getExpression())).getRightHandSide();
	    visitor.visit(compilationUnit);
	    visitor.endVisit(superMethodInvocation);
	    

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        int x;\n" +
	        "        x = Verifier.nondetInt();\n" +
	        "    }\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitEnhancedForStatement_removesOrReplacesLoop() throws Exception {
	    // Example source code containing an enhanced for loop
	    String source = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        for (int num : new int[]{1, 2, 3}) {\n" +
	        "            System.out.println(num);\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Set up the visitor
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, null, null, "SVCOMP", source);
	    EnhancedForStatement enhancedForStatement =
	       		 (EnhancedForStatement)(((MethodDeclaration)((TypeDeclaration)
					compilationUnit.types().get(0)).bodyDeclarations().get(0))
					.getBody().statements().get(0));
	    visitor.visit(enhancedForStatement);

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "    }\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitFieldAccess_keepsThisExpressionWithSimpleName() throws Exception {
	    // Example source code containing a field access
	    String source = 
	        "public class TestClass {\n" +
	        "    SomeClass field = this.field;\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(null);

	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, null, "SVCOMP", source);
        FieldAccess fieldAccess = (FieldAccess)((VariableDeclarationFragment)((FieldDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0)).fragments().get(0)).getInitializer();
	    visitor.visit(compilationUnit);
        visitor.visit(fieldAccess);

	    // Apply the changes (or lack thereof) made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    SomeClass field = this.field;\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitFieldAccess_replacesPrimitiveThisExpressionWithSymbolic() throws Exception {
	    // Example source code containing a field access
	    String source = 
	        "public class TestClass {\n" +
	        "    int field = this.field;\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());
	    
	    // Set up mock behavior
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);

	    // Mock the current method and return type
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(mockType);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);

	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, null, "SVCOMP", source);
        FieldAccess fieldAccess = (FieldAccess)((VariableDeclarationFragment)((FieldDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0)).fragments().get(0)).getInitializer();
	    visitor.visit(compilationUnit);
        visitor.visit(fieldAccess);

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    int field = Verifier.nondetInt();\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitInstanceofExpression_replacesWithFalse() throws Exception {
	    // Example source code containing an InstanceofExpression inside an if-statement
	    String source = 
	        "public class TestClass {\n" +
	        "    public void someMethod(Object obj) {\n" +
	        "        if (obj instanceof String) {\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());
	    
	    // Set up mock behavior
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);
	    TypeChecker mockTypeChecker = mock(TypeChecker.class);

	    // Mock the current method and return type
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(mockType);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);
	    when(mockTypeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);

	    // Apply the visitor
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, mockTypeChecker, "SVCOMP", source);
	    InstanceofExpression instanceofExpression = (InstanceofExpression)((IfStatement)((MethodDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0))
        		.getBody().statements().get(0)).getExpression();
	    visitor.visit(compilationUnit);
	    visitor.endVisit(instanceofExpression);
	    
	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod(Object obj) {\n" +
	        "        if (Verifier.nondetBoolean()) {\n" +
	        "        }\n" +
	        "    }\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_visitPostfixExpression_removesDisallowedExpressions() throws Exception {
	    // Example source code containing a PostfixExpression inside an expression statement
	    String source = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        x++;\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	 // Set up mock behavior
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);
	    TypeChecker mockTypeChecker = mock(TypeChecker.class);

	    // Mock the current method and return type
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(null);

	    // Apply the visitor
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, null, "SVCOMP", source);
	    PostfixExpression postfixExpression = (PostfixExpression)((ExpressionStatement)((MethodDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0))
        		.getBody().statements().get(0)).getExpression();
	    visitor.visit(postfixExpression);

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "    }\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}

	@Test
	public void test_endVisitQualifiedName_replacesDisallowedTypes() throws Exception {
	    // Example source code containing a QualifiedName in an assignment
	    String source = 
	        "public class TestClass {\n" +
	        "    int value = SomeClass.someField;\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Set up mock behavior
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);
	    TypeChecker mockTypeChecker = mock(TypeChecker.class);

	    // Mock the current method and return type
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(mockType);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);
	    when(mockTypeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);

	    // Apply the visitor
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, mockTypeChecker, "SVCOMP", source);
	    // Mock behavior for replaceInteger
        QualifiedName qualifiedName = (QualifiedName)
        		((VariableDeclarationFragment)((FieldDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0))
        		.fragments().get(0)).getInitializer();
        visitor.visit(compilationUnit);
        visitor.endVisit(qualifiedName);
	    
	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    int value = Verifier.nondetInt();\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	/*
	 * This is an overly-simplified test and decoupling this node should be a TODO 
	 */
	@Test
	public void test_visitSimpleName_initializesUninitializedFields() throws Exception {
	    // Example source code containing an uninitialized field usage
	    String source = 
	        "public class TestClass {\n" +
	        "    int value;\n" +
	        "    public void someMethod() {\n" +
	        "        int x = value;\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Set up mock behavior
	    SymbolTable mockSymbolTable = mock(SymbolTable.class);
	    VarSTE mockVarSTE = mock(VarSTE.class);
	    MethodSTE mockMethodSTE = mock(MethodSTE.class);
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);
	    TypeChecker mockTypeChecker = mock(TypeChecker.class);

	    // Mock the current method and return type
	    when(mockSymbolTable.getVarSTE(Mockito.any())).thenReturn(mockVarSTE);
	    when(mockSymbolTable.getMethodSTE(Mockito.any())).thenReturn(mockMethodSTE);
	    when(mockVarSTE.isFieldVar()).thenReturn(true);
	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(mockType);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);
	    when(mockTypeChecker.allowedType(Mockito.any(Type.class))).thenReturn(true);


	    // Apply the visitor
	    TransformVisitor visitor = new TransformVisitor(mockSymbolTable, rewriter, mockTypeTable, mockTypeChecker, "SVCOMP", source);
	    MethodDeclaration methodDeclaration = (MethodDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(1);
	    SimpleName simpleName = (SimpleName) 
	      		((VariableDeclarationFragment)((VariableDeclarationStatement)((MethodDeclaration)((TypeDeclaration)
	            		compilationUnit.types().get(0)).bodyDeclarations().get(1))
	            		.getBody().statements().get(0)).fragments().get(0)).getInitializer();
	    visitor.visit(compilationUnit);
	    visitor.visit(methodDeclaration); // setting up initializedVars
	    visitor.endVisit(methodDeclaration);
	    visitor.visit(simpleName);

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    int value;\n" +
	        "    public void someMethod() {\n" +
	        "        int value = Verifier.nondetInt();\n" +
	        "		int x = value;\n" +
	        "    }\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	
	@Test
	public void test_endVisitInfixExpression_replacesInvalidTypes() throws Exception {
	    // Example source code containing an InfixExpression in an assignment
	    String source = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        int x = unknownVar + 5;\n" +
	        "    }\n" +
	        "}";

	    // Parse the source code
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setSource(source.toCharArray());
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

	    // Create a document and an ASTRewrite instance
	    Document document = new Document(source);
	    ASTRewrite rewriter = ASTRewrite.create(compilationUnit.getAST());

	    // Set up mock behavior
	    TypeTable mockTypeTable = mock(TypeTable.class);
	    TypeChecker mockTypeChecker = mock(TypeChecker.class);
	    PrimitiveType mockType = mock(PrimitiveType.class);

	    when(mockTypeTable.getNodeType(Mockito.any())).thenReturn(mockType);
	    when(mockType.getPrimitiveTypeCode()).thenReturn(PrimitiveType.INT);
	    when(mockTypeChecker.allowedType(Mockito.any(Type.class))).thenReturn(false);

	    // Apply the visitor
	    TransformVisitor visitor = new TransformVisitor(null, rewriter, mockTypeTable, mockTypeChecker, "SVCOMP", source);
	    InfixExpression infixExpression = (InfixExpression)
        		((VariableDeclarationFragment)((VariableDeclarationStatement)((MethodDeclaration)((TypeDeclaration)
        		compilationUnit.types().get(0)).bodyDeclarations().get(0))
        		.getBody().statements().get(0)).fragments().get(0)).getInitializer();
	    visitor.visit(compilationUnit);
	    visitor.endVisit(infixExpression);

	    // Apply the changes made by the visitor
	    TextEdit edits = rewriter.rewriteAST(document, null);
	    edits.apply(document);

	    // Verify the updated source code
	    String expectedSource = 
	        "public class TestClass {\n" +
	        "    public void someMethod() {\n" +
	        "        int x = Verifier.nondetInt();\n" +
	        "    }\n" +
	        "}";
	    
	    assertEquals(expectedSource.trim(), document.get().trim());
	}
	

}
