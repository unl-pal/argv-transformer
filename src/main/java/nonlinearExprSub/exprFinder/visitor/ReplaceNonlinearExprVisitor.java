package nonlinearExprSub.exprFinder.visitor;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import java.util.HashMap;

public class ReplaceNonlinearExprVisitor extends ASTVisitor {
    private HashMap<String, Integer> exprToVarmap;
    private AST ast;
    private ASTRewrite rewriter;

    public ReplaceNonlinearExprVisitor(HashMap<String, Integer> exprToVarmap,
                                       ASTRewrite rewriter, AST ast) {
        this.exprToVarmap = exprToVarmap;
        this.rewriter = rewriter;
        this.ast = ast;
    }

    @Override
    public void endVisit(InfixExpression node) {
        Integer symbVarNum = exprToVarmap.get(node.toString());
        if (symbVarNum == null) {
            return;
        }

        String name = "x" + symbVarNum;
        SimpleName exprSymbVar = ast.newSimpleName(name);

        rewriter.replace(node, exprSymbVar, null);
    }
}
