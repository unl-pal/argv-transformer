package tests.Transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import transform.SymbolTable.Scope;
import transform.SymbolTable.SymbolSTE;

class ScopeTest {

	String source;
	ASTParser parser;
	AST ast;
	CompilationUnit cu;
	
	@BeforeEach
	public void setUp() throws IOException {
		source = new String(Files.readAllBytes(new File("/home/MariaPaquin/project/SPF_Transformations/src/tests/TransformTests/ScopeTest.java").toPath()));
		parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		cu = (CompilationUnit) parser.createAST(null);
		ast =  cu.getAST();
	}
	
	@Test
	void testRoot() throws IOException {
		Scope root = new Scope(null);
		Scope mainClass = new Scope(root);
		SymbolSTE f1 = new SymbolSTE("field1", ast.newPrimitiveType(PrimitiveType.VOID) , true);
		SymbolSTE f2 = new SymbolSTE("field2", ast.newSimpleType(ast.newName("Animal")), false);
		
		mainClass.put("field1", f1);
		mainClass.put("field2", f2);
				
		assertEquals(mainClass.get("field1").isAllowedType(), true);
		assertEquals(mainClass.get("field2").isAllowedType(), false);
		
		assertEquals(mainClass.getNumVars(), 2);
	}
	
	@Test
	void testMethod() throws IOException {
		Scope root = new Scope(null);

		Scope mainClass = new Scope(root);
		
		SymbolSTE f1 = new SymbolSTE("field1", ast.newPrimitiveType(PrimitiveType.VOID) , true);
		SymbolSTE f2 = new SymbolSTE("field2", ast.newSimpleType(ast.newName("Animal")), false);
		
		mainClass.put("field1", f1);
		mainClass.put("field2",f2);
		
		Scope method = new Scope(mainClass);
		
		SymbolSTE l1 = new SymbolSTE("local1", ast.newPrimitiveType(PrimitiveType.VOID) , true);
		SymbolSTE l2 = new SymbolSTE("local2", ast.newSimpleType(ast.newName("Animal")), false);
		SymbolSTE l3 = new SymbolSTE("field1", ast.newSimpleType(ast.newName("Dog")), false);
		
		method.put("local1", l1);
		method.put("local2", l2);
		method.put("field1", l3);
		
		assertEquals(method.getParent(), mainClass);
		assertEquals(mainClass.getParent(), root);

		assertEquals(method.get("local1").isAllowedType(), true);
		assertEquals(method.get("local2").isAllowedType(), false);
		assertEquals(method.get("field1").isAllowedType(), false);
		
		assertEquals(mainClass.get("field1"), f1);
		assertEquals(mainClass.get("field2"), f2);
		assertEquals(mainClass.get("local1"), null);
		assertEquals(mainClass.get("local2"), null);

		assertEquals(method.get("local1"), l1);
		assertEquals(method.get("local2"), l2);
		assertEquals(method.get("field1"), l3);
		assertEquals(method.get("field2"), f2);
				
		assertEquals(mainClass.getNumVars(), 2);
		assertEquals(method.getNumVars(), 3);
	}
	
	@Test
	void testInnerClass() throws IOException {
		Scope root = new Scope(null);

		Scope mainClass = new Scope(root);
		SymbolSTE f1 = new SymbolSTE("field1", ast.newPrimitiveType(PrimitiveType.VOID) , true);
		mainClass.put("field1", f1);
		
		Scope method = new Scope(mainClass);
		SymbolSTE l1 = new SymbolSTE("local1", ast.newPrimitiveType(PrimitiveType.VOID) , true);
		method.put("local1", l1);
		
		Scope innerClass = new Scope(mainClass);
		SymbolSTE f2 = new SymbolSTE("field2", ast.newPrimitiveType(PrimitiveType.VOID) , true);
		innerClass.put("field2", f2);
		
		Scope method2 = new Scope(innerClass);
		SymbolSTE l2 = new SymbolSTE("local2", ast.newPrimitiveType(PrimitiveType.VOID) , true);
		method2.put("local2", l2);
		
		assertEquals(mainClass.get("field1"), f1);
		assertEquals(mainClass.get("field2"), null);
		assertEquals(mainClass.get("local1"), null);
		assertEquals(mainClass.get("local2"), null);
		
		assertEquals(method.get("field1"), f1);
		assertEquals(method.get("field2"), null);
		assertEquals(method.get("local1"), l1);
		assertEquals(method.get("local2"), null);
		
		assertEquals(innerClass.get("field1"), f1);
		assertEquals(innerClass.get("field2"), f2);
		assertEquals(innerClass.get("local1"), null);
		assertEquals(innerClass.get("local2"), null);
		
		assertEquals(method2.get("field1"), f1);
		assertEquals(method2.get("field2"), f2);
		assertEquals(method2.get("local1"), null);
		assertEquals(method2.get("local2"), l2);
		
	}
	
	@Test
	void testFor() throws IOException {
		Scope root = new Scope(null);
		SymbolSTE f1 = new SymbolSTE("field1", ast.newPrimitiveType(PrimitiveType.VOID), true);
		
		root.put("field1", f1);

		Scope method = new Scope(root);
		SymbolSTE l1 = new SymbolSTE("local1", ast.newPrimitiveType(PrimitiveType.VOID), true);
		
		method.put("local1", l1);
				
		Scope forLoop = new Scope(method);
	}

}
