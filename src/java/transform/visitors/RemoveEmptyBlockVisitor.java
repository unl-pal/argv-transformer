package transform.visitors;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

/**
 * Safely removes empty blocks in a variety of contexts to clean up the document after transformation.
 * Used in conjunction with DisallowedMethodAndFieldVisitor.
 */
public class RemoveEmptyBlockVisitor extends ASTVisitor {
    private final ASTRewrite rewriter;

    public RemoveEmptyBlockVisitor(ASTRewrite rewriter) {
        this.rewriter = rewriter;
    }

    /**
     * Removes empty blocks from if statements. 
     * Additional logic is required to handle cases between then and else statements.
     */
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
            if (!(elseStmt instanceof Block)) {
                // Wrap in a block if not already a block
                Block thenBlock = ast.newBlock();
				thenBlock.statements().add(elseCopy);
	            rewriter.set(node, IfStatement.THEN_STATEMENT_PROPERTY, thenBlock, null);
            } else {
                rewriter.set(node, IfStatement.THEN_STATEMENT_PROPERTY, elseCopy, null);
            }
            rewriter.remove(elseStmt, null);
            return false;
        }
        // Case 2: non-empty then, empty else -> remove else
        else if (!thenEmpty && elseStmt != null && elseEmpty) {
            rewriter.remove(elseStmt, null);
        }
        // Case 3: empty then and empty else
        else if (thenEmpty && (elseEmpty || elseStmt == null)) {
        	rewriter.remove(node, null);
        	return false;
        }
        // Otherwise, leave as-is
        return super.visit(node);
    }
    
    /**
     * Removes empty methods. Constructors are not removed.
     */
    @Override
    public boolean visit(MethodDeclaration node) {
        if (node.getBody().statements().isEmpty() && !node.isConstructor()) {
	        rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    /**
     * Removes nested empty blocks.
     */
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

    /**
     * Removes empty while statements.
     */
    @Override
    public boolean visit(WhileStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

    /**
     * Removes empty for statements.
     */
    @Override
    public boolean visit(ForStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

	/**
	 * Removes empty enhanced for statements.
	 */
    @Override
    public boolean visit(EnhancedForStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

	/**
	 * Removes empty do statements.
	 */
    @Override
    public boolean visit(DoStatement node) {
        Statement body = node.getBody();
        if (body instanceof Block && ((Block) body).statements().isEmpty()) {
            rewriter.remove(node, null);
        }
        return super.visit(node);
    }

	/**
	 * Removes empty synchronized statements.
	 */
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
     * i.e. !a -> a, a -> !a
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