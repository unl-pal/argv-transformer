package tests.Transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.junit.jupiter.api.Test;

import sourceAnalysis.AnalyzedFile;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeTable;
import transform.visitors.TypeCheckingVisitor;
import transform.visitors.TypeTableVisitor;
import transform.visitors.MethodCallParamVisitor;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeCollectVisitor;

class Tests {

	@Test
	public void testAllowedTypesCollector1() throws IOException, MalformedTreeException, BadLocationException {
		File file = new File("./src/tests/Transformer/TestArray.java");
		String source = new String(Files.readAllBytes(file.toPath()));
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		AST ast = cu.getAST();
		ASTRewrite rewriter = ASTRewrite.create(ast);
		
		SymbolTableVisitor symTableVisitor = new SymbolTableVisitor();
		cu.accept(symTableVisitor);
		SymbolTable rootScope = symTableVisitor.getRoot();
		
		TypeTableVisitor typeTableVisitor = new TypeTableVisitor(rootScope);
		cu.accept(typeTableVisitor);
		TypeTable typeTable = typeTableVisitor.getTypeTable();
		
		TypeCollectVisitor typeAggregateVisitor = new TypeCollectVisitor();
		cu.accept(typeAggregateVisitor);
		TypeChecker typeChecker = typeAggregateVisitor.getTypeChecker();
		
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor(rootScope, rewriter, typeTable, typeChecker);
		cu.accept(typeCheckingVisitor);
		rewriter = typeCheckingVisitor.getRewriter();

		
		try {
			source = new String(Files.readAllBytes(file.toPath()));
			Document document = new Document(source);

			TextEdit edits = rewriter.rewriteAST(document, null);
			edits.apply(document);
			System.out.println(document.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
