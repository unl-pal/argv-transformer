package nonlinearExprSub.exprFinder.visitor;

import nonlinearExprSub.exprFinder.expr.ExpressionLiteral;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.SimpleName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NonlinearExprVisitor extends ASTVisitor {
    private List<ExpressionLiteral> nonlinearVarExpr;
    private HashMap<String, Integer> exprMap;
    private int varCount;

    public NonlinearExprVisitor() {
        nonlinearVarExpr = new ArrayList<>();
        exprMap = new HashMap<>();
        varCount = 0;
    }

    public List<ExpressionLiteral> getNonlinearVarExpr() {
        return nonlinearVarExpr;
    }

    public int getVarCount() {
        return varCount;
    }

    @Override
    public boolean visit(InfixExpression node) {
        Expression lhs = node.getLeftOperand();
        Expression rhs = node.getRightOperand();
        InfixExpression.Operator op = node.getOperator();

        if (!containsVar(lhs) || !containsVar(rhs)) {
            return true;
        }

        if((op != InfixExpression.Operator.TIMES) &&
                (op != InfixExpression.Operator.DIVIDE) &&
                (op != InfixExpression.Operator.REMAINDER)){
            return true;
        }

        ExpressionLiteral expressionLiteral = new ExpressionLiteral(node.toString());

        List<String> varsUsed = getVarsUsed(node);
        expressionLiteral.setVarsUsed(varsUsed);

        boolean existingExpr = false;
        for (ExpressionLiteral e : nonlinearVarExpr) {
            if (e.getExpr().equals(expressionLiteral.getExpr())) {
                existingExpr = true;
            }
        }
        if(!existingExpr) {
            nonlinearVarExpr.add(expressionLiteral);
            exprMap.put(node.toString(), varCount++);
        }
        return true;
    }

    private boolean containsVar(Expression e) {
        if (e instanceof SimpleName) {
            return true;
        }

        if (e instanceof InfixExpression) {
            InfixExpression infix = (InfixExpression) e;
            return (containsVar(infix.getLeftOperand()) || containsVar(infix.getRightOperand()));
        }

        // TODO: What else can expression be?

        return false;
    }

    private List<String> getVarsUsed(InfixExpression node) {
        List<String> vars = new ArrayList<>();
        ASTVisitor visitor= new ASTVisitor() {
            @Override
            public boolean visit(SimpleName name) {
                vars.add(name.getIdentifier());
                return false;
            }
        };
        node.accept(visitor);
        return vars;
    }

    public HashMap<String, Integer> getExprMap() {
        return exprMap;
    }
}
