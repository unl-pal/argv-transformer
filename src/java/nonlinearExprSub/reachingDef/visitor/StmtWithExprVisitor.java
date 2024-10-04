package nonlinearExprSub.reachingDef.visitor;

import org.eclipse.jdt.core.dom.*;
import nonlinearExprSub.reachingDef.Constraint.Term.ConstraintTerm;
import nonlinearExprSub.reachingDef.Constraint.Term.EntryLabel;
import nonlinearExprSub.reachingDef.Constraint.Term.SetDifference;
import nonlinearExprSub.reachingDef.ConstraintCreator.ConstraintTermFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Find entry labels for statements that contain
 * nonlinear variable infix expressions.
 */
public class StmtWithExprVisitor extends ASTVisitor {

    private List<EntryLabel> entryLablesWithExpr;
    private ConstraintTerm currStmt;
    private ConstraintTermFactory variableFactory;

    public StmtWithExprVisitor(ConstraintTermFactory variableFactory) {
        entryLablesWithExpr = new ArrayList<>();
        this.variableFactory = variableFactory;
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

    public List<EntryLabel> getEntryLablesWithExpr() {
        return entryLablesWithExpr;
    }

    @Override
    public boolean visit(Assignment node) {
        ASTNode parent = node.getParent();
        if (!variableFactory.containsEntryLabel(parent)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(parent);
        return true;
    }

    @Override
    public boolean visit(DoStatement node) {
        if (!variableFactory.containsEntryLabel(node)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(node);
        return true;
    }

    @Override
    public boolean visit(EnhancedForStatement node) {
        if (!variableFactory.containsEntryLabel(node)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(node);
        return true;
    }

    @Override
    public boolean visit(ForStatement node) {
        if (!variableFactory.containsEntryLabel(node)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(node);
        return true;
    }

    @Override
    public boolean visit(IfStatement node) {
        if (!variableFactory.containsEntryLabel(node)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(node);
        return true;
    }

    @Override
    public boolean visit(MethodInvocation node) {
        if (!(node.getParent() instanceof ExpressionStatement)) {
            return false;
        }
        ASTNode parent = node.getParent();
        if (!variableFactory.containsEntryLabel(parent)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(parent);
        return true;
    }

    @Override
    public boolean visit(PostfixExpression node) {
        if (!(node.getParent() instanceof ExpressionStatement)) {
            return false;
        }
        ASTNode parent = node.getParent();
        if (!variableFactory.containsEntryLabel(parent)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(parent);
        return true;
    }

    @Override
    public boolean visit(PrefixExpression node) {
        if (!(node.getParent() instanceof ExpressionStatement)) {
            return false;
        }
        ASTNode parent = node.getParent();
        if (!variableFactory.containsEntryLabel(parent)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(parent);
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        if (!variableFactory.containsEntryLabel(node)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(node);
        return true;
    }

    @Override
    public boolean visit(WhileStatement node) {
        if (!variableFactory.containsEntryLabel(node)) {
            return false;
        }
        currStmt = variableFactory.createEntryLabel(node);
        return true;
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

        if (currStmt instanceof EntryLabel) {
            entryLablesWithExpr.add((EntryLabel) currStmt);
        } else if (currStmt instanceof SetDifference) {
            ConstraintTerm entry = ((SetDifference) currStmt).getEntryTerm();
            entryLablesWithExpr.add((EntryLabel) entry);
        }

        return true;
    }
}
