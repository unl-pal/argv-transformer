package transform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
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
import transform.visitors.DisallowedMethodAndFieldVisitor;
import transform.visitors.FinalizerVisitor;
import transform.visitors.PreprocessingVisitor;
import transform.visitors.RemoveEmptyBlockVisitor;
import transform.visitors.SymbolTableVisitor;
import transform.visitors.TypeCollectVisitor;
import static transform.Main.logger;
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
    logger.enterContext("Transformer");
  }

  /**
   * Annotates files with filtered comment
   * before each class
   * We assume those classed are compiled
   */
  public void annotateFiles() {
    Iterator<File> itr = (directory != null ? FileUtils.iterateFiles(directory, new String[] { "java" }, true)
        : files.iterator());

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

        // getting to the insert position
        TypeDeclaration typeDec = (TypeDeclaration) cu.types().get(0);
        // update

        ListRewrite listRewrite = rewriter.getListRewrite(typeDec, TypeDeclaration.MODIFIERS2_PROPERTY);
        Statement comment = (Statement) rewriter.createStringPlaceholder("/** filtered by ARG-V */\n",
            ASTNode.EMPTY_STATEMENT);
        listRewrite.insertFirst(comment, null);

        Document document = new Document(source);
        TextEdit edits = rewriter.rewriteAST(document, null);
        edits.apply(document);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(document.get());
        out.flush();
        out.close();

      } catch (Exception e) {
        logger.logln("Exception " + e + " while transforming file " + file.getAbsolutePath(), 1);
      }
    }
  }

  /**
   * Transform each file by editing its AST structure then apply
   * modifications to the source file.
   */
  public void transformFiles(int minTypeExpr, int minTypeCond, int minTypeParams, CType type) {
    Iterator<File> itr = (directory != null ? FileUtils.iterateFiles(directory, new String[] { "java" }, true)
        : files.iterator());

    while (itr.hasNext()) {

      File file = (File) itr.next();
      logger.logln("Current file name: " + file.getName(), 6);

      try {
        String source = new String(Files.readAllBytes(file.toPath()));
        String inputSource = Main.source;
        if (inputSource.endsWith(".java")) {
          Path path = Paths.get(inputSource);
          inputSource = (path.getParent() != null) ? path.getParent().toString() : ".";
        }
        FilenameUtils.removeExtension(Main.source);

        String[] classPath = { Paths.get("build", "classes", "java", "main").toAbsolutePath().toString(),
            Paths.get("build", "classes", "java", "test").toAbsolutePath().toString() };
        String[] sourcePath = { Paths.get(inputSource).toAbsolutePath().toString(),
            Paths.get("src", "java").toAbsolutePath().toString() };

        // preprocessing that prunes tree
        // - removes all anonymous classes
        // - rewrites one-line if statements to block statements
        // - removes nested classes
        // - removes methods with disallowed return types or parameters based on TyperChecker.allowedType() (if note constructor)
        ASTParser preprocessingParser = getParser(source, sourcePath, classPath, file);

        CompilationUnit preprocessingCu = (CompilationUnit) preprocessingParser.createAST(null);
        preprocessingCu.recordModifications();

// Debug: Check if bindings are resolved
            IProblem[] problems = preprocessingCu.getProblems();
            if (problems.length > 0) {
                logger.logln("=== Compilation Problems ===",6);
                for (IProblem problem : problems) {
                    logger.logln("  " + problem.getMessage() + " (line " + problem.getSourceLineNumber() + ")", 6);
                }
                logger.logln("============================",6);
            }

// Debug: Check a simple type binding
            preprocessingCu.accept(new ASTVisitor() {
                @Override
                public boolean visit(SimpleType node) {
                    ITypeBinding binding = node.resolveBinding();
//                    logger.logln("DEBUG: Type " + node + " -> binding=" + (binding == null ? "NULL" : binding.getQualifiedName()), 6);
                    return true;
                }
            });
        ASTRewrite preprocessingRewriter = ASTRewrite.create(preprocessingCu.getAST());

        logger.enterContext("PreprocessingVisitor");
        PreprocessingVisitor preprocessingVisitor = new PreprocessingVisitor(preprocessingRewriter,
            preprocessingCu.getAST());
        preprocessingCu.accept(preprocessingVisitor);

        // document based off of original source. Each time a transformation is applied,
        // a new document is created
        Document document = new Document(source);
        TextEdit edits = preprocessingRewriter.rewriteAST(document, null);
        edits.apply(document);

        String transformSource = document.get();

        ASTParser parser = getParser(transformSource, sourcePath, classPath, file);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        cu.recordModifications();
        ASTRewrite rewriter = ASTRewrite.create(ast);

        logger.enterContext("TypeCollectVisitor");
        // those are the same as in filtering
        // setting up bindings, symbol table, etc. for the transformation
        TypeCollectVisitor typeCollectVisitor = new TypeCollectVisitor();
        cu.accept(typeCollectVisitor);
        TypeChecker typeChecker = typeCollectVisitor.getTypeChecker();

        logger.enterContext("SymbolTableVisitor");
        SymbolTableVisitor symTableVisitor = new SymbolTableVisitor(typeChecker);
        cu.accept(symTableVisitor);
        SymbolTable rootScope = symTableVisitor.getRoot();

        logger.enterContext("TypeTableVisitor");
        TypeTableVisitor typeTableVisitor = new TypeTableVisitor(rootScope, typeChecker);
        cu.accept(typeTableVisitor);
        TypeTable typeTable = typeTableVisitor.getTypeTable();

        logger.enterContext("TransformVisitor");
        // the actual transformation
        TransformVisitor transformVisitor = new TransformVisitor(rootScope, rewriter, typeTable,
            typeChecker, target, transformSource);
        cu.accept(transformVisitor);
        rewriter = transformVisitor.getRewriter();

        // rewriting document based off of transformation. Future ASTs will be based off
        // of this document.
        edits = rewriter.rewriteAST(document, null);
        edits.apply(document);

        // removing comments to avoid duplicates when we put them back in. This
        // adding/removal process in necessary
        // to preserve provenance information
        String commentSource = document.get();
        ASTParser commentParser = ASTParser.newParser(AST.JLS8);
        commentParser.setSource(commentSource.toCharArray());
        commentParser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit commentCu = (CompilationUnit) commentParser.createAST(null);
        commentCu.recordModifications();

        logger.enterContext("CommentPruningVisitor");
        CommentPruningVisitor commentPruningVisitor = new CommentPruningVisitor(commentSource);
        commentCu.accept(commentPruningVisitor);

        commentPruningVisitor.getCommentsToDelete().apply(document);

        // cleaning up empty blocks and disallowed methods iteratively until nothing
        // more can be removed
        do {
          String editedSource = document.get();
          ASTParser cleanupParser = getParser(editedSource, sourcePath, classPath, file);

          CompilationUnit cleanupCu = (CompilationUnit) cleanupParser.createAST(null);
          cleanupCu.recordModifications();

          ASTRewrite cleanupRewriter = ASTRewrite.create(cleanupCu.getAST());

          logger.enterContext("RemoveEmptyBlockVisitor");
          RemoveEmptyBlockVisitor removeEmptyBlockVisitor = new RemoveEmptyBlockVisitor(cleanupRewriter);
          cleanupCu.accept(removeEmptyBlockVisitor);

          logger.enterContext("DisallowedMethodAndFieldVisitor");
          DisallowedMethodAndFieldVisitor disallowedMethodVisitor = new DisallowedMethodAndFieldVisitor(cleanupRewriter,
              transformVisitor.getDisallowed(), typeChecker);
          cleanupCu.accept(disallowedMethodVisitor);

          edits = cleanupRewriter.rewriteAST(document, null);
          edits.apply(document);
        } while (edits.getLength() > 0);

        // check if the new AST meets selection criteria requirements
        // If some method in the class now do not meet the requirement,
        // then we insert a comment before that method stating that
        // after the transformation it becomes not a good one.
        // We cannot just remove them - otherwise we need to go into
        // an iterative process until a fixed point has been reached.
        // If all method in a class becomes not good, then we don't output
        // that class at all

        String finalSource = document.get();
        ASTParser finalizerParser = getParser(finalSource, sourcePath, classPath, file);

        CompilationUnit finalCu = (CompilationUnit) finalizerParser.createAST(null);
        finalCu.recordModifications();

        logger.enterContext("TypeCollectVisitor");
        typeCollectVisitor = new TypeCollectVisitor();
        finalCu.accept(typeCollectVisitor);
        typeChecker = typeCollectVisitor.getTypeChecker();

        logger.enterContext("SymbolTableVisitor");
        symTableVisitor = new SymbolTableVisitor(typeChecker);
        finalCu.accept(symTableVisitor);
        rootScope = symTableVisitor.getRoot();

        logger.enterContext("TypeTableVisitor");
        typeTableVisitor = new TypeTableVisitor(rootScope, typeChecker);
        finalCu.accept(typeTableVisitor);
        typeTable = typeTableVisitor.getTypeTable();

        ASTRewrite rewriterFinal = ASTRewrite.create(finalCu.getAST());

        logger.enterContext("CommentAddingVisitor");
        CommentAddingVisitor commentAddingVisitor = new CommentAddingVisitor(rewriterFinal,
            transformVisitor.getPreImportComments(), transformVisitor.getPostImportComments());
        finalCu.accept(commentAddingVisitor);

        logger.enterContext("FinalizerVisitor");
        // cannot use old typeTable, things has changed
        AnalyzedFile af = new AnalyzedFile(file);
        FinalizerVisitor fv = new FinalizerVisitor(finalCu.getAST(), rewriterFinal, typeChecker, af, typeTable,
            minTypeExpr, minTypeCond, minTypeParams, type);
        finalCu.accept(fv);

        logger.enterContext("Transformer");
        // transformation for usable methods finished. now checking how these compare to config
        // AnalyzedFile has gone through to look for valid methods
        logger.logln("Suitable methods " + af.getSuitableMethods().size() + " in " + file, 1);
        if (af.getSuitableMethods().size() > 0) {

          for (Object typeDecl : finalCu.types()) {
            MethodDeclaration[] methodDeclArr = ((TypeDeclaration) typeDecl).getMethods();
            // if(methodDeclArr.length > af.getSuitableMethods().size()) {
            // there are methods that are in the class, but do not meet the filtering
            // criteria
            // we need add a comment before their declarations
            for (MethodDeclaration md : methodDeclArr) {
              boolean found = false;
              for (AnalyzedMethod am : af.getSuitableMethods()) {
                if (am.getMethodDeclaration().equals(md)) {
                  found = true;
                  break;
                }
              }
              // check if such method has not been found, then insert comments
              if (found) {
                logger.logln("Found suitable MDecl: " + md.getName(), 2);
                ListRewrite listRewrite = rewriterFinal.getListRewrite(md, MethodDeclaration.MODIFIERS2_PROPERTY);
                Statement comment = (Statement) rewriterFinal.createStringPlaceholder("/** ARG-V: suitable */\n",
                    ASTNode.EMPTY_STATEMENT);
                listRewrite.insertFirst(comment, null);
              }
            }
            // }
          } // end for all types

          // Rewrite the file
          edits = rewriterFinal.rewriteAST(document, null);
          edits.apply(document);
          //

          BufferedWriter out = new BufferedWriter(new FileWriter(file));
          String finalFinal = document.get();
          out.write(document.get());
          out.flush();
          out.close();
        } else {
          logger.logln("No suitable methods after transformation. Discarding " + file.getPath(), 2);
          file.delete();
        }

      } catch (Exception e) {
        logger.logln("Exception " + e + " while transforming file " + file.getPath(), 1);
        e.printStackTrace();
      }
    }
  }

public static ASTParser getParser(String source, String[] sourcePath, String[] classPath, File file) {
      logger.enterContext("Transformer.getParser");
    ASTParser parser = ASTParser.newParser(AST.JLS8);
    parser.setSource(source.toCharArray());
    parser.setKind(ASTParser.K_COMPILATION_UNIT);
    parser.setResolveBindings(true);
    parser.setBindingsRecovery(true);
    parser.setStatementsRecovery(true);
    Map<String, String> options = JavaCore.getOptions();
    options.put(JavaCore.COMPILER_SOURCE, "1.8");
    parser.setCompilerOptions(options);
    parser.setUnitName(file.getAbsolutePath());

    // Get JDK home from system property (set by Gradle) or fall back to java.home
    String jdkHome = System.getProperty("jdkHome", System.getProperty("java.home"));

    // Find rt.jar - check multiple locations for cross-platform compatibility
    Path rtJar = findRtJar(jdkHome);
    if (rtJar == null) {
        throw new RuntimeException("rt.jar not found. Ensure you're using JDK 8. Searched in: " + jdkHome);
    }

    String[] newClassPath = Arrays.copyOf(classPath, classPath.length + 1);
    newClassPath[newClassPath.length - 1] = rtJar.toAbsolutePath().toString();

    String[] encodings = new String[sourcePath.length];
    Arrays.fill(encodings, "UTF-8");
//    logger.logln("=== ASTParser Environment Debug ===", 6);
//    logger.logln("jdkHome: " + jdkHome, 6);
//    logger.logln("rt.jar: " + rtJar.toAbsolutePath(), 6);
//    logger.logln("rt.jar exists: " + Files.exists(rtJar), 6);
//    logger.logln("unitName: " + file.getAbsolutePath(), 6);
//    logger.logln("sourcePath (" + sourcePath.length + "):", 6);
//    for (String sp : sourcePath) {
//        logger.logln("  " + sp + " [exists=" + new File(sp).exists() + "]", 6);
//    }
//    logger.logln("classpath (" + newClassPath.length + "):", 6);
//    for (String cp : newClassPath) {
//        logger.logln("  " + cp + " [exists=" + new File(cp).exists() + "]", 6);
//    }
//    logger.logln("encodings length: " + encodings.length, 6);
//    logger.logln("===================================", 6);

    parser.setEnvironment(newClassPath, sourcePath, encodings, true);
    logger.exitContext("Transformer.getParser");
    return parser;
}

/**
 * Find rt.jar in various possible locations within a JDK/JRE installation.
 * Handles differences between JDK vendors and OS layouts.
 *
 * @param javaHome The JDK or JRE home directory
 * @return Path to rt.jar, or null if not found
 */
private static Path findRtJar(String javaHome) {
    // Possible rt.jar locations across different JDK vendors and OS
    String[] possiblePaths = {
        "lib/rt.jar",           // Standard JDK 8 layout
        "jre/lib/rt.jar",       // Some JDK 8 distributions (Temurin, etc.)
        "../lib/rt.jar",        // If java.home points to jre subdirectory
        "../jre/lib/rt.jar"     // Alternative layout
    };

    Path baseDir = Paths.get(javaHome);
    for (String relativePath : possiblePaths) {
        Path candidate = baseDir.resolve(relativePath).normalize();
        if (Files.exists(candidate)) {
            return candidate;
        }
    }

    // For JDK 9+, rt.jar doesn't exist - check for jrt-fs.jar as indicator
    Path jrtFs = baseDir.resolve("lib/jrt-fs.jar");
    if (Files.exists(jrtFs)) {
        throw new RuntimeException("JDK 9+ detected. This tool requires JDK 8 with rt.jar.");
    }

    return null;
}
  //
  // public static ASTParser getParser(String source, String[] sourcePath, String[] classPath, File file) {
  //   ASTParser parser = ASTParser.newParser(AST.JLS8);
  //   parser.setSource(source.toCharArray());
  //   parser.setKind(ASTParser.K_COMPILATION_UNIT);
  //   parser.setResolveBindings(true);
  //   parser.setBindingsRecovery(true);
  //   parser.setStatementsRecovery(true);
  //   Map<String, String> options = JavaCore.getOptions();
  //   options.put(JavaCore.COMPILER_SOURCE, "1.8");
  //   parser.setCompilerOptions(options);
  //   parser.setUnitName(file.getAbsolutePath());
  //   String javaHome = System.getProperty("java.home");
  //   Path rtJar = Paths.get(javaHome, "lib", "rt.jar");
  //   if (!rtJar.toFile().exists()) {
  //     rtJar = 
  //   }
  //   String[] newClassPath = Arrays.copyOf(classPath, classPath.length + 1);
  //   newClassPath[newClassPath.length - 1] = rtJar.toAbsolutePath().toString();
  //   // nps: jdt require encoding array mathces sorucepath length
  //   String[] encodings = new String[sourcePath.length];
  //   Arrays.fill(encodings, "UTF-8");
  //   // ... inside getParser, just before parser.setEnvironment(...)
  //   //
  //   // logger.logln("=== DEBUG: ASTParser Environment ===");
  //   // logger.logln("Target File (Unit Name): " + file.getAbsolutePath());
  //   //
  //   // // 1. Check Array Lengths (MUST MATCH for sources and encodings)
  //   // logger.logln(String.format("Arrays: SourcePath[%d], Encodings[%d], ClassPath[%d]",
  //   //     sourcePath.length, encodings.length, newClassPath.length));
  //   //
  //   // if (sourcePath.length != encodings.length) {
  //   //   logger.logln("!!! ERROR: SourcePath and Encodings arrays must be the same length!");
  //   // }
  //   //
  //   // // 2. Validate Classpath Existence
  //   // logger.logln("-- Classpath Entries --");
  //   // for (String cp : newClassPath) {
  //   //   File f = new File(cp);
  //   //   if (!f.exists()) {
  //   //     logger.logln("  [MISSING] " + cp); // <--- LOOK FOR THIS
  //   //   } else {
  //   //     logger.logln("  [OK] " + cp);
  //   //   }
  //   // }
  //   //
  //   // // 3. Validate Sourcepath Existence
  //   // logger.logln("-- Sourcepath Entries --");
  //   // for (String sp : sourcePath) {
  //   //   File f = new File(sp);
  //   //   if (!f.exists()) {
  //   //     logger.logln("  [MISSING] " + sp);
  //   //   } else {
  //   //     logger.logln("  [OK] " + sp);
  //   //   }
  //   // }
  //   // logger.logln("====================================");
  //
  //   // parser.setEnvironment(newClassPath, sourcePath, encodings, true);
  //   parser.setEnvironment(newClassPath, sourcePath, encodings, true);
  //
  //   return parser;
  // }
  //
  /**
   * Helper method for renaming a class to Main.java
   * 
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
   * Helper method for renaming instance variables. Useful for when files get
   * renamed during postprocessing.
   * For example, if TestClass.java gets renamed to Main.java, this method will
   * rename the appropriate variable declarations.
   * 
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

      @Override
        public boolean visit(MethodDeclaration node) {
          if (node.getName().getIdentifier().equals(oldClassName)) {
              rewriter.set(node, MethodDeclaration.NAME_PROPERTY, ast.newSimpleName("Main"), null);
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
