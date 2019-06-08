package tests.TypeTable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeTable;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeTableVisitor;

public class MainTest {
	static String source;
	static ASTParser parser;
	static AST ast;
	static CompilationUnit cu;
	static TypeTable table;
	

	public static void main(String[] args) throws IOException {
		source = new String(Files.readAllBytes(new File("/home/MariaPaquin/project/SPF_Transformations/src/tests/TypeTable/A.java").toPath()));
		parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		cu = (CompilationUnit) parser.createAST(null);
		ast =  cu.getAST();
		
		SymbolTableVisitor symTableVisitor = new SymbolTableVisitor();
		cu.accept(symTableVisitor);
		SymbolTable rootScope = symTableVisitor.getRoot();
		
		TypeTableVisitor visitor = new TypeTableVisitor(rootScope);
		cu.accept(visitor);
		
		table = visitor.getTypeTable();
		
		TestVisitor testVisitor = new TestVisitor(table);
		cu.accept(testVisitor);
		
	}
}
