package nonlinearExprSub.exprFinder.main;

import nonlinearExprSub.exprFinder.expr.ExpressionLiteral;
import nonlinearExprSub.exprFinder.expr.KillSet;
import nonlinearExprSub.exprFinder.visitor.KillsetVisitor;
import nonlinearExprSub.exprFinder.visitor.NonlinearExprVisitor;
import nonlinearExprSub.exprFinder.visitor.InitSymbVarsVisitor;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Driver {

    public static void main(String[] args) throws IOException {

        File file = new File("./tests/While.java");
        String source = new String(Files.readAllBytes(file.toPath()));
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();

        ASTRewrite rewriter = ASTRewrite.create(ast);

        List<TypeDeclaration> types = cu.types();
        for (TypeDeclaration type : types) {
            for (MethodDeclaration methodDeclaration : type.getMethods()) {

                NonlinearExprVisitor exprVisitor = new NonlinearExprVisitor();
                methodDeclaration.accept(exprVisitor);
                List<ExpressionLiteral> exprList = exprVisitor.getNonlinearVarExpr();
                HashMap<String, Integer> exprToVarMap = exprVisitor.getExprMap();
                int varCount = exprVisitor.getVarCount();

                KillsetVisitor killsetVisitor = new KillsetVisitor(exprList);
                methodDeclaration.accept(killsetVisitor);
                HashMap<ASTNode, KillSet> killMap = killsetVisitor.getKillMap();

                InitSymbVarsVisitor rewriteVisitor = new InitSymbVarsVisitor(exprToVarMap,
                        killMap, rewriter, ast);
                methodDeclaration.accept(rewriteVisitor);
            }
        }

        Document document = new Document(source);
        TextEdit edits = rewriter.rewriteAST(document, null);
        try {
            edits.apply(document);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        System.out.println(document.get());

/*        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(document.get());
        out.flush();
        out.close();*/
    }
}

