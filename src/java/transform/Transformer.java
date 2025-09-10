package transform;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import sourceAnalysis.AnalyzedFile;
import sourceAnalysis.AnalyzedMethod;
import transform.SymbolTable.SymbolTable;
import transform.TypeChecking.TypeChecker;
import transform.TypeChecking.TypeChecker.CType;
import transform.TypeChecking.TypeTable;
import transform.visitors.TransformVisitor;
import transform.visitors.TypeTableVisitor;
import transform.visitors.CommentAddingVisitor;
import transform.visitors.CommentPruningVisitor;
import transform.visitors.FinalizerVisitor;
import transform.visitors.RemoveEmptyBlockVisitor;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeCollectVisitor;
/**
 * Class to transform Java files into compilable, SPF suitable benchmarks.
 * 
 * @author mariapaquin
 *
 */
public class Transformer {

	private ArrayList<File> files;
	private File directory;
	private String target;

	/**
	 * Create a new Transformer. 
	 * 
	 * @param files A list of files to transform. 
	 */
	public Transformer(ArrayList<File> files, String target) {
		this.files = files;
		this.target = target;
	}

	/**
	 * Create a new Transformer. 
	 * 
	 * @param directory A directory containing files to transform. 
	 */
	public Transformer(File directory) {
		this.directory = directory;
	}
	
	/**
	 * Annotates files with filtered comment
	 * before each class 
	 * We assume those classed are compiled
	 */
	public void annotateFiles() {
		Iterator<File> itr = (directory != null ? 
				FileUtils.iterateFiles(directory, new String[] { "java" }, true): files.iterator());
		
		while (itr.hasNext()) {
			
			File file = (File) itr.next();

			try {
				String source = new String(Files.readAllBytes(file.toPath()));
				ASTParser parser = ASTParser.newParser(AST.JLS8);
				parser.setSource(source.toCharArray());
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				parser.setStatementsRecovery(true);

				CompilationUnit cu = (CompilationUnit) parser.createAST(null);
				
				AST ast = cu.getAST();
				ASTRewrite rewriter = ASTRewrite.create(ast);
				
				//getting to the insert position
				TypeDeclaration typeDec = (TypeDeclaration)cu.types().get(0);
				//update	
				
				ListRewrite listRewrite = rewriter.getListRewrite(typeDec, TypeDeclaration.MODIFIERS2_PROPERTY);
				//rewriterComm.get
				Statement comment = (Statement) rewriter.createStringPlaceholder("/** filtered by ARG-V */\n", ASTNode.EMPTY_STATEMENT);
				listRewrite.insertFirst(comment, null);
				
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
	

	/**
	 * Transform each file by editing its AST structure then apply
	 * modifications to the source file. 
	 */
	public void transformFiles(int minTypeExpr, int minTypeCond, int minTypeParams, CType type) {
		Iterator<File> itr = (directory != null ? 
				FileUtils.iterateFiles(directory, new String[] { "java" }, true): files.iterator());
		
		while (itr.hasNext()) {
			
			File file = (File) itr.next();
			System.out.println("Current file name: " + file.getName());

			try {
				String source = new String(Files.readAllBytes(file.toPath()));
				String inputSource = Main.source;
                if (inputSource.endsWith(".java")) {
                    Path path = Paths.get(inputSource);
                    inputSource = path.getParent().toString();
                }
                FilenameUtils.removeExtension(Main.source);

                String[] classPath = {Paths.get("build", "classes", "java", "main").toString()};
                String[] sourcePath = { Paths.get(inputSource).toString() , Paths.get("src").toString()};
                
                
				ASTParser parser = getParser(source, sourcePath, classPath, file);
				
				

				CompilationUnit cu = (CompilationUnit) parser.createAST(null);
				AST ast = cu.getAST();
				cu.recordModifications();
				ASTRewrite rewriter = ASTRewrite.create(ast);


				//those are the same as in filtering
				TypeCollectVisitor typeCollectVisitor = new TypeCollectVisitor();
				cu.accept(typeCollectVisitor);
				TypeChecker typeChecker = typeCollectVisitor.getTypeChecker();
				
				SymbolTableVisitor symTableVisitor = new SymbolTableVisitor(typeChecker);
				cu.accept(symTableVisitor);
				SymbolTable rootScope = symTableVisitor.getRoot();

				TypeTableVisitor typeTableVisitor = new TypeTableVisitor(rootScope, typeChecker);
				cu.accept(typeTableVisitor);
				TypeTable typeTable = typeTableVisitor.getTypeTable();
				//now we have each variable resolved to implied types
//				if(file.getName().contains("HEAP")) {
//					System.out.println("Type table ");
//					for(Entry<ASTNode, Type> e : typeTable.getTable().entrySet()) {
//						if(e.getKey() instanceof SimpleName) {
//							if(((SimpleName)e.getKey()).getIdentifier().contains("currentSize")){
//							System.out.println(e.getValue() + "\t" + e.getKey()+ "\t" + e.getKey().getParent());
//							}
//						}
//						
//					}
//				}
				
				//the actual transformation
				TransformVisitor transformVisitor = new TransformVisitor(rootScope, rewriter, typeTable,
						typeChecker, target, source);
				cu.accept(transformVisitor);
				rewriter = transformVisitor.getRewriter();
				
				Document document = new Document(source);
				TextEdit edits = rewriter.rewriteAST(document, null);
				edits.apply(document);
				
				// removing comments to avoid duplicates when we put them back in. This adding/removal process in necessary
				// to preserve provenance information
				String commentSource = document.get();
				ASTParser commentParser = ASTParser.newParser(AST.JLS8);
				commentParser.setSource(commentSource.toCharArray());
				commentParser.setKind(ASTParser.K_COMPILATION_UNIT);
				CompilationUnit commentCu = (CompilationUnit) commentParser.createAST(null);
				commentCu.recordModifications();
				
				CommentPruningVisitor commentPruningVisitor = new CommentPruningVisitor(commentSource);
				commentCu.accept(commentPruningVisitor);
				
				commentPruningVisitor.getCommentsToDelete().apply(document);
				
				
				
				// cleaning up empty blocks and putting comments back in
				String editedSource = document.get();
				ASTParser cleanupParser = getParser(editedSource, sourcePath, classPath, file);
				
				CompilationUnit cleanupCu = (CompilationUnit) cleanupParser.createAST(null);
				cleanupCu.recordModifications();
				
				ASTRewrite rewriterComm = ASTRewrite.create(cleanupCu.getAST());
				
				RemoveEmptyBlockVisitor removeEmptyBlockVisitor = new RemoveEmptyBlockVisitor(rewriterComm);
				cleanupCu.accept(removeEmptyBlockVisitor);
								
				edits = rewriterComm.rewriteAST(document, null);
				edits.apply(document);
				
				// check if the new AST meets selection criteria requirements
				//If some method in the class now do not meet the requirement, 
				//then we insert a comment before that method stating that
				//after the transformation it becomes not a good one.
				//We cannot just remove them - otherwise we need to go into
				//an iterative process until a fixed point has been reached.
				//If all method in a class becomes not good, then we don't output 
				//that class at all
				
				String finalSource = document.get();
				ASTParser finalizerParser = getParser(finalSource, sourcePath, classPath, file);

				CompilationUnit finalCu = (CompilationUnit) finalizerParser.createAST(null);
				finalCu.recordModifications();
								
				typeCollectVisitor = new TypeCollectVisitor();
				finalCu.accept(typeCollectVisitor);
				typeChecker = typeCollectVisitor.getTypeChecker();
				
				symTableVisitor = new SymbolTableVisitor(typeChecker);
				finalCu.accept(symTableVisitor);
				rootScope = symTableVisitor.getRoot();

				typeTableVisitor = new TypeTableVisitor(rootScope, typeChecker);
				finalCu.accept(typeTableVisitor);
				typeTable = typeTableVisitor.getTypeTable();
				
				ASTRewrite rewriterFinal = ASTRewrite.create(finalCu.getAST());
				
                CommentAddingVisitor commentAddingVisitor = new CommentAddingVisitor(rewriterFinal, transformVisitor.getPreImportComments(), transformVisitor.getPostImportComments());
                finalCu.accept(commentAddingVisitor);
				
				//cannot use old typeTable, things has changed
				AnalyzedFile af = new AnalyzedFile(file);
				FinalizerVisitor fv = new FinalizerVisitor(af, typeTable, minTypeExpr, minTypeCond, minTypeParams, type);
				finalCu.accept(fv);
				
				
				System.out.println("Suitable methods " + af.getSuitableMethods().size() + " in " + file);
				if(af.getSuitableMethods().size() > 0) {
					
					for(Object typeDecl : finalCu.types()) {
					MethodDeclaration[] methodDeclArr = ((TypeDeclaration)typeDecl).getMethods();
					//if(methodDeclArr.length > af.getSuitableMethods().size()) {
						//there are methods that are in the class, but do not meet the filtering criteria
						//we need add a comment before their declarations
						for(MethodDeclaration md : methodDeclArr) {
							boolean found = false;
							for(AnalyzedMethod am : af.getSuitableMethods()) {
								if(am.getMethodDeclaration().equals(md)) {
									found = true;
									break;
								}
							}
							//check if such method has not been found, then insert comments
							if(found) {
								System.out.println("Found suitable MDecl");
								ListRewrite listRewrite = rewriterFinal.getListRewrite(md, MethodDeclaration.MODIFIERS2_PROPERTY);
								Statement comment;
								if (md.getName().getIdentifier().equals("main")) {
									comment = (Statement) rewriterFinal.createStringPlaceholder("/** This main was generated by ARG-V */\n", ASTNode.EMPTY_STATEMENT);
								} else {
									comment = (Statement) rewriterFinal.createStringPlaceholder("/** ARG-V: suitable */\n", ASTNode.EMPTY_STATEMENT);
								}
								listRewrite.insertFirst(comment, null);
							}
						}
					//}
					}//end for all types

					//Rewrite the file
					edits = rewriterFinal.rewriteAST(document, null);
					edits.apply(document);
//					

					BufferedWriter out = new BufferedWriter(new FileWriter(file));
					out.write(document.get());
					out.flush();
					out.close();
				}

			} catch (Exception e) {				
				System.out.println("Exception " + e + " while transforming file " + file.getAbsolutePath());
				e.printStackTrace();
			}
		}
	}
	
	public static ASTParser getParser(String source, String[] sourcePath, String[] classPath, File file) {
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);
        parser.setStatementsRecovery(true);
        Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, "1.8");
        parser.setCompilerOptions(options);
        parser.setUnitName(file.getPath());
        parser.setEnvironment(classPath, sourcePath, new String[] { "UTF-8", "UTF-8" }, true);

		return parser;
	}
	
	/**
	 * Helper method for renaming a class to Main.java
	 * @param source the full file text to be renamed
	 * @return the full file text with instantiations renamed to Main
	 */
	public static String updateClassName(String source) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        cu.recordModifications();

        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(TypeDeclaration node) {
                if (!node.isInterface()) {
                    node.setName(cu.getAST().newSimpleName("Main"));
                }
                return true;
            }
        });

        Document doc = new Document(source);
        TextEdit edits = cu.rewrite(doc, null);
        try {
            edits.apply(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc.get();
    }
	
	/**
	 * Helper method for renaming instance variables. Useful for when files get renamed during postprocessing.
	 * For example, if TestClass.java gets renamed to Main.java, this method will rename the appropriate variable declarations.
	 * @param source the full file's text
	 * @return the full file's text with information renamed.
	 */
	public static String renameInstanceVariables(String source, String oldClassName) {
        ASTParser parser = ASTParser.newParser(AST.JLS8); // Use appropriate Java version
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(source.toCharArray());
        parser.setResolveBindings(true);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        ASTRewrite rewriter = ASTRewrite.create(ast);

        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(ClassInstanceCreation node) {
                Type type = node.getType();
                if (type instanceof SimpleType) {
                    SimpleType simpleType = (SimpleType) type;
                    Name typeName = simpleType.getName();

                    if (typeName.getFullyQualifiedName().equals(oldClassName)) {
                        // Rename the instantiated class to "Main"
                        SimpleName newNameNode = ast.newSimpleName("Main");
                        SimpleType newType = ast.newSimpleType(newNameNode);
                        rewriter.replace(simpleType, newType, null);
                    }
                }
                return super.visit(node);
            }

            @Override
            public boolean visit(VariableDeclarationStatement node) {
                Type type = node.getType();
                if (type instanceof SimpleType) {
                    SimpleType simpleType = (SimpleType) type;
                    Name typeName = simpleType.getName();

                    if (typeName.getFullyQualifiedName().equals(oldClassName)) {
                        // Rename the declared type to "Main"
                        SimpleName newNameNode = ast.newSimpleName("Main");
                        SimpleType newType = ast.newSimpleType(newNameNode);
                        rewriter.replace(simpleType, newType, null);
                    }
                }
                return super.visit(node);
            }
        });

        Document document = new Document(source);
        try {
            TextEdit edits = rewriter.rewriteAST(document, null);
            edits.apply(document);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document.get();
    }
}
