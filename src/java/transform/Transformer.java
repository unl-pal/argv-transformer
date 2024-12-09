package transform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
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
import transform.visitors.FinilizerVisitor;
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
				ASTParser parser = ASTParser.newParser(AST.JLS8);
				parser.setSource(source.toCharArray());
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				parser.setResolveBindings(true);
				parser.setBindingsRecovery(true);
				Map<String, String> options = JavaCore.getOptions();
				options.put(JavaCore.COMPILER_SOURCE, "1.8");
				parser.setCompilerOptions(options);
				parser.setUnitName(file.getPath());
				
				String[] classPath = {Paths.get("build", "classes").toString()};
				// TODO: when actually running, this should be "suitablePrgms" instead of "test"
				String[] sourcePath = { Paths.get("test").toString() , Paths.get("src").toString()};
				parser.setEnvironment(classPath, sourcePath, new String[] { "UTF-8", "UTF-8" }, true);

				CompilationUnit cu = (CompilationUnit) parser.createAST(null);
				AST ast = cu.getAST();
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
				TransformVisitor typeCheckingVisitor = new TransformVisitor(rootScope, rewriter, typeTable,
						typeChecker, target);
				cu.accept(typeCheckingVisitor);
				rewriter = typeCheckingVisitor.getRewriter();
				
		
				
				
				//now we can write it

				Document document = new Document(source);
				TextEdit edits = rewriter.rewriteAST(document, null);
				edits.apply(document);
				
				
				
				// check if the new AST meets selection criteria requirements
				//If some method in the class now do not meet the requirement, 
				//then we insert a comment before that method stating that
				//after the transformation it becomes not a good one.
				//We cannot just remove them - otherwise we need to go into
				//an iterative process until a fixed point has been reached.
				//If all method in a class becomes not good, then we don't output 
				//that class at all
				
				//edits.
				String editedSource = document.get();
				ASTParser parserR = ASTParser.newParser(AST.JLS8);
				parserR.setSource(editedSource.toCharArray());
				parserR.setKind(ASTParser.K_COMPILATION_UNIT);
				parserR.setResolveBindings(true);
				parserR.setBindingsRecovery(true);
				parserR.setCompilerOptions(options);
				parserR.setUnitName(file.getPath());
				parserR.setEnvironment(classPath, sourcePath, new String[] { "UTF-8", "UTF-8" }, true);

				CompilationUnit cuR = (CompilationUnit) parserR.createAST(null);

				

				typeCollectVisitor = new TypeCollectVisitor();
				cuR.accept(typeCollectVisitor);
				typeChecker = typeCollectVisitor.getTypeChecker();
				
				symTableVisitor = new SymbolTableVisitor(typeChecker);
				cuR.accept(symTableVisitor);
				rootScope = symTableVisitor.getRoot();

				typeTableVisitor = new TypeTableVisitor(rootScope, typeChecker);
				cuR.accept(typeTableVisitor);
				typeTable = typeTableVisitor.getTypeTable();
				//cannot use old typeTable, things has changed
				AnalyzedFile af = new AnalyzedFile(file);
				FinilizerVisitor fv = new FinilizerVisitor(af, typeTable, minTypeExpr, minTypeCond, minTypeParams, type);
				cuR.accept(fv);
				
				ASTRewrite rewriterComm = ASTRewrite.create(cuR.getAST());
				
				//getting to the insert position
				PackageDeclaration packDec = cuR.getPackage();
				//update
				
				
				ListRewrite listRewrite = rewriterComm.getListRewrite(packDec, PackageDeclaration.ANNOTATIONS_PROPERTY);
				//rewriterComm.get
				Statement comment = (Statement) rewriterComm.createStringPlaceholder("/** filtered and transformed by ARG-V */\n", ASTNode.EMPTY_STATEMENT);
				listRewrite.insertFirst(comment, null);
				
				
				System.out.println("Suiatable methods " + af.getSuitableMethods().size() + " in " + file);
				if(af.getSuitableMethods().size() > 0) {
					
					for(Object typeDecl : cuR.types()) {
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
								listRewrite = rewriterComm.getListRewrite(md, MethodDeclaration.MODIFIERS2_PROPERTY);
								comment = (Statement) rewriterComm.createStringPlaceholder("/** ARG-V: suitable */\n", ASTNode.EMPTY_STATEMENT);
								listRewrite.insertFirst(comment, null);
							}
						}
					//}
					}//end for all types
//					
					edits = rewriterComm.rewriteAST(document, null);
					edits.apply(document);
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
}
