package nonlinearExprSub;

import nonlinearExprSub.exprFinder.expr.ExpressionLiteral;
import nonlinearExprSub.exprFinder.expr.KillSet;
import nonlinearExprSub.exprFinder.visitor.KillsetVisitor;
import nonlinearExprSub.exprFinder.visitor.NonlinearExprVisitor;
import nonlinearExprSub.exprFinder.visitor.ReplaceNonlinearExprVisitor;
import nonlinearExprSub.exprFinder.visitor.InitSymbVarsVisitor;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import nonlinearExprSub.reachingDef.Constraint.Constraint;
import nonlinearExprSub.reachingDef.Constraint.Term.DefinitionLiteral;
import nonlinearExprSub.reachingDef.Constraint.Term.EntryLabel;
import nonlinearExprSub.reachingDef.ConstraintCreator.ConstraintTermFactory;
import nonlinearExprSub.reachingDef.Solving.ConstraintSolver;
import nonlinearExprSub.reachingDef.visitor.ConstraintVisitor;
import nonlinearExprSub.reachingDef.visitor.StmtWithExprVisitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
/**
 * Given a directory of benchmark programs, iterate through java files and 
 * apply transformation to each.
 * 
 * @author mariapaquin
 *
 */
public class Transformer {
    private AST ast;
    private ASTRewrite rewriter;
    private CompilationUnit cu;
    private String source;
    private List<ASTNode> reachingDef;
    private List<String> symbVars;

    public static void main(String[] args) throws IOException {
        File destDir = new File("/home/MariaPaquin/project/paclab-transformer.git/benchmarks/");

        Iterator<File> file_itr = FileUtils.iterateFiles(destDir, new String[] { "java" }, true);

        file_itr = FileUtils.iterateFiles(destDir, new String[] { "java" }, true);

        file_itr.forEachRemaining(file -> {
            try {
                transform(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Transforms the Java file by substituting nonlinear variable 
     * expressions with new symbolic variables.
     * 
     * @param file Java file to transform
     * @throws IOException
     */
    private static void transform(File file) throws IOException {
        Transformer transformer = new Transformer();

        // (1) insert initializations and reassignments of symbolic variables
        transformer.setupAST(file);
        transformer.initializeReassignSymbVar();
        transformer.applyEdits(file);

        // (2) remove non-reaching assignments/definitions
        transformer.setupAST(file);
        transformer.findReachingDefinitions();
        transformer.removeNonreachingDefinitions();

        // (3) replace nonlinear variable expressions with symbolic variables
        transformer.replaceExpressions();
        
        transformer.applyEdits(file);
    }
    
    /**
     * Initialize a new symbolic variable for each nonlinear variable expression 
     * found in the method. 
     * 
     * Reassign a symbolic variable after its corresponding expression is killed.
     */
    private void initializeReassignSymbVar() {
        List<TypeDeclaration> types = cu.types();

        for (TypeDeclaration type : types) {

            for (MethodDeclaration methodDeclaration : type.getMethods()) {

                // get a list of nonlinear variable expressions, each is an ExpressionLiteral object
                // map each to a new symbolic variable (string), ie what it will be replaced with
                NonlinearExprVisitor exprVisitor = new NonlinearExprVisitor();
                methodDeclaration.accept(exprVisitor);

                List<ExpressionLiteral> exprList = exprVisitor.getNonlinearVarExpr();
                HashMap<String, Integer> exprToVarMap = exprVisitor.getExprMap();

                // map each applicable node (assignment, var dec, postfix expr, etc) to the expression it kills
                KillsetVisitor killsetVisitor = new KillsetVisitor(exprList);
                methodDeclaration.accept(killsetVisitor);
                HashMap<ASTNode, KillSet> killMap = killsetVisitor.getKillMap();

                // (1) initialize each symbolic variable at the beginning of the method
                // (2) Get the expressions killed for each statement. Re-assign the symbolic
                // variable associated with each expression in its kill set.
                InitSymbVarsVisitor visitor = new InitSymbVarsVisitor(exprToVarMap,
                        killMap, rewriter, ast);
                methodDeclaration.accept(visitor);

                symbVars = visitor.getSymbVars();
            }
        }
    }

    /**
     * Find definitions/assignments of symbolic variables that reach expressions with 
     * nonlinear symbolic expressions.
     */
    private void findReachingDefinitions() {
        reachingDef = new ArrayList<>();
        ConstraintVisitor visitor = new ConstraintVisitor();
        cu.accept(visitor);

        Set<String> variables = new HashSet<String>();

        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(SimpleName node) {
                if (node.getLocationInParent() == TypeDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == MethodDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == SingleVariableDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == QualifiedName.NAME_PROPERTY ||
                        node.getLocationInParent() == QualifiedName.QUALIFIER_PROPERTY ||
                        node.getLocationInParent() == PackageDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == SimpleType.NAME_PROPERTY ||
                        node.getLocationInParent() == ImportDeclaration.NAME_PROPERTY ||
                        node.getLocationInParent() == TypeParameter.NAME_PROPERTY) {
                    return true;
                }
                variables.add(node.getIdentifier());
                return true;
            }
        });

        ArrayList<Constraint> constraints = visitor.getConstraints();

        ConstraintSolver solver = new ConstraintSolver(constraints, variables);

        solver.buildConstraintGraph();

        solver.initializeDefinitionSet();

        solver.processWorkList();

        // pick out only the labels that contain variable infix expressions
        ConstraintTermFactory variableFactory = visitor.getVariableFactory();

        StmtWithExprVisitor nodeLabelExprVisitor = new StmtWithExprVisitor(variableFactory);
        cu.accept(nodeLabelExprVisitor);

        // we only care about the reaching definitions of statements that contain
        // nonlinear variable expressions
        List<EntryLabel> entryLabels = nodeLabelExprVisitor.getEntryLablesWithExpr();

        for (EntryLabel entry : entryLabels) {
            Set<String> prgmVars = entry.reachingDefSet.getVariables();
            for (String var: prgmVars) {
                for (DefinitionLiteral d : entry.reachingDefSet.get(var)) {
                    if (!reachingDef.contains(d.getNode())) {
                        reachingDef.add(d.getNode());
                    }
                }
            }
        }
    }

    /**
     * Remove non-reaching definitions/assignments of symbolic variables.
     */
    private void removeNonreachingDefinitions() {
        cu.accept(new ASTVisitor() {
            @Override
            public boolean visit(Assignment node) {

                // only need to worry about removing re-assignments of
                // the symbolic variables we declared
                if(!symbVars.contains(node.getLeftHandSide().toString())){
                    return false;
                }

                ASTNode parent = node.getParent();

                if (!reachingDef.contains(parent)) {
                    rewriter.remove(parent, null);
                }
                return true;
            }
        });
    }

    /**
     * Replace nonlinear variable expressions with corresponding symbolic variables. 
     */
    private void replaceExpressions() {
        List<TypeDeclaration> types = cu.types();

        for (TypeDeclaration type : types) {

            for (MethodDeclaration methodDeclaration : type.getMethods()) {

                // get a list of nonlinear variable expressions, each is an ExpressionLiteral object
                // map each to a new symbolic variable (string), ie what it will be replaced with
                NonlinearExprVisitor exprVisitor = new NonlinearExprVisitor();
                methodDeclaration.accept(exprVisitor);

                HashMap<String, Integer> exprToVarMap = exprVisitor.getExprMap();

                // replace each infix expression with its corresponding symbolic variable
                ReplaceNonlinearExprVisitor visitor = new ReplaceNonlinearExprVisitor(exprToVarMap, rewriter, ast);
                methodDeclaration.accept(visitor);
            }
        }
    }

    /**
     * Apply the source code changes to the file.
     * 
     * @param file
     * @throws IOException
     */
    private void applyEdits(File file) throws IOException {
        Document document = new Document(source);
        TextEdit edits = rewriter.rewriteAST(document, null);
        try {
            edits.apply(document);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(document.get());
        out.flush();
        out.close();
    }
    
    /**
     * Setup the AST and AST rewriter. 
     * @param file
     * @throws IOException
     */
    private void setupAST(File file) throws IOException {
        source = new String(Files.readAllBytes(file.toPath()));
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        cu = (CompilationUnit) parser.createAST(null);
        ast = cu.getAST();

        rewriter = ASTRewrite.create(ast);
    }
}
