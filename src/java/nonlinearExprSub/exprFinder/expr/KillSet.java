package nonlinearExprSub.exprFinder.expr;

import org.eclipse.jdt.core.dom.ASTNode;

import java.util.List;

public class KillSet {

    private List<ExpressionLiteral> exprs;
    private ASTNode node;

    public KillSet(ASTNode node) {
        this.node = node;
    }

    public List<ExpressionLiteral> getExprs() {
        return exprs;
    }

    public void setExprs(List<ExpressionLiteral> exprs) {
        this.exprs = exprs;
    }
}
