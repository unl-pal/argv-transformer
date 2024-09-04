package nonlinearExprSub.exprFinder.visitor;

import nonlinearExprSub.exprFinder.expr.ExpressionLiteral;
import nonlinearExprSub.exprFinder.expr.KillSet;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class KillsetVisitor extends ASTVisitor {
    private List<ExpressionLiteral> availableExpressions;
    private HashMap<ASTNode, KillSet> killMap;

    public KillsetVisitor(List<ExpressionLiteral> availableExpressions) {
        this.availableExpressions = availableExpressions;
        killMap = new HashMap<>();
    }

    @Override
    public boolean visit(Assignment node) {
        KillSet ks = createKillSet(node);

        String lhs = node.getLeftHandSide().toString();
        List<ExpressionLiteral>  killedExprs = getExpressionsInvolving(lhs);
        ks.setExprs(killedExprs);

        return true;
    }


    @Override
    public boolean visit(PostfixExpression node) {
        if (!(node.getParent() instanceof ExpressionStatement)) {
            return false;
        }
        KillSet ks = createKillSet(node);

        String lhs = node.getOperand().toString();
        List<ExpressionLiteral>  killedExprs = getExpressionsInvolving(lhs);

        ks.setExprs(killedExprs);

        return true;
    }

    @Override
    public boolean visit(PrefixExpression node) {
        if (!(node.getParent() instanceof ExpressionStatement)) {
            return false;
        }
        KillSet ks = createKillSet(node);

        String lhs = node.getOperand().toString();
        List<ExpressionLiteral>  killedExprs = getExpressionsInvolving(lhs);

        ks.setExprs(killedExprs);

        return true;
    }


    @Override
    public boolean visit(VariableDeclarationStatement node) {
        KillSet ks = createKillSet(node);
        VariableDeclarationFragment fragment = ((List<VariableDeclarationFragment>)
                node.fragments()).get(0);

        String lhs = fragment.getName().getIdentifier();
        List<ExpressionLiteral>  killedExprs = getExpressionsInvolving(lhs);

        ks.setExprs(killedExprs);

        return true;
    }


    private List<ExpressionLiteral> getExpressionsInvolving(String lhs) {
        List<ExpressionLiteral> exprsInvolvingLhs = new ArrayList<>();
        for (ExpressionLiteral expr : availableExpressions) {
            if (expr.involves(lhs)) {
                exprsInvolvingLhs.add(expr);
            }
        }
        return exprsInvolvingLhs;
    }

    public KillSet createKillSet(ASTNode node) {
        KillSet ks = killMap.get(node);
        if (ks == null) {
            ks = new KillSet(node);
            killMap.put(node, ks);
        }

        return ks;
    }
    public HashMap<ASTNode, KillSet> getKillMap() {
        return killMap;
    }
}
