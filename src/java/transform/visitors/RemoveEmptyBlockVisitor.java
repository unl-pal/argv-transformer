package transform.visitors;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

public class RemoveEmptyBlockVisitor extends ASTVisitor {
    private final ASTRewrite rewriter;

    public RemoveEmptyBlockVisitor(ASTRewrite rewriter) {
        this.rewriter = rewriter;
    }

    @Override
    public boolean visit(IfStatement node) {
        AST ast = node.getAST();
        Statement thenStmt = node.getThenStatement();
        Statement elseStmt = node.getElseStatement();

        boolean thenEmpty = thenStmt instanceof Block && ((Block) thenStmt).statements().isEmpty();
        boolean elseEmpty = elseStmt instanceof Block && ((Block) elseStmt).statements().isEmpty();

        // Case 1: empty then, non-empty else -> invert condition, move else to then, drop else
        if (thenEmpty && elseStmt != null && !elseEmpty) {
            // Invert the condition
            Expression inverted = invertCondition(ast, node.getExpression());
            rewriter.set(node, IfStatement.EXPRESSION_PROPERTY, inverted, null);

            // Copy else subtree into then
            Statement elseCopy = (Statement) ASTNode.copySubtree(ast, elseStmt);
            rewriter.set(node, IfStatement.THEN_STATEMENT_PROPERTY, elseCopy, null);
            rewriter.remove(elseStmt, null);
        }
        // Case 2: non-empty then, empty else -> remove else
        else if (!thenEmpty && elseStmt != null && elseEmpty) {
            rewriter.remove(elseStmt, null);
        }
        // Case 3: empty then and empty else
        else if (thenEmpty && (elseEmpty || elseStmt == null)) {
        	rewriter.remove(node, null);
        }
        // Otherwise, leave as-is
        return super.visit(node);
    }
    
    @Override
    public boolean visit(MethodDeclaration node) {
        if (node.getBody().statements().isEmpty()) {
	        rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(Block node) {
        // Only remove pure nested empty blocks
        if (node.statements().isEmpty() && node.getParent() instanceof Block) {
            Block parent = (Block) node.getParent();
            ListRewrite lr = rewriter.getListRewrite(parent, Block.STATEMENTS_PROPERTY);
            lr.remove(node, null);
            return false; // don't descend into removed block
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(WhileStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(ForStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(EnhancedForStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(DoStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    @Override
    public boolean visit(SynchronizedStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    /**
     * Inverts a boolean expression: unwraps '!' or adds it.
     */
    private Expression invertCondition(AST ast, Expression expr) {
        if (expr instanceof PrefixExpression) {
            PrefixExpression pe = (PrefixExpression) expr;
            if (pe.getOperator() == PrefixExpression.Operator.NOT) {
                return (Expression) ASTNode.copySubtree(ast, pe.getOperand());
            }
        }
        ParenthesizedExpression wrapped = ast.newParenthesizedExpression();
        wrapped.setExpression((Expression) ASTNode.copySubtree(ast, expr));
        PrefixExpression notExpr = ast.newPrefixExpression();
        notExpr.setOperator(PrefixExpression.Operator.NOT);
        notExpr.setOperand((Expression) ASTNode.copySubtree(ast, wrapped));
        return notExpr;
    }
}