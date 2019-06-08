package transform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import logging.Logger;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeTable;
import transform.visitors.TypeCheckingVisitor;
import transform.visitors.TypeTableVisitor;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeCollectVisitor;

public class Transformer {

	private ArrayList<File> files;
	private File directory;

	public Transformer(ArrayList<File> files) {
		this.files = files;
	}

	public Transformer(File directory) {
		this.directory = directory;
	}

	public void transformFiles() {
		Iterator itr = (directory != null ? 
				FileUtils.iterateFiles(directory, new String[] { "java" }, true): files.iterator());
		
		while (itr.hasNext()) {
			
			File file = (File) itr.next();
			
			try {
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

				TypeCollectVisitor typeCollectVisitor = new TypeCollectVisitor();
				cu.accept(typeCollectVisitor);
				TypeChecker typeChecker = typeCollectVisitor.getTypeChecker();

				TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor(rootScope, rewriter, typeTable,
						typeChecker);
				cu.accept(typeCheckingVisitor);
				rewriter = typeCheckingVisitor.getRewriter();

				Document document = new Document(source);
				TextEdit edits = rewriter.rewriteAST(document, null);
				edits.apply(document);
				BufferedWriter out = new BufferedWriter(new FileWriter(file));

				out.write(document.get());
				out.flush();
				out.close();

			} catch (Exception e) {
				System.out.println("Exception " + e + " while transforming file " + file.getAbsolutePath());
			}
		}
	}
}
