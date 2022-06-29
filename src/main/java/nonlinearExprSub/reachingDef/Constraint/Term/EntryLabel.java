package nonlinearExprSub.reachingDef.Constraint.Term;

import org.eclipse.jdt.core.dom.*;

import java.util.Set;


/**
 * RD_entry[n]
 *
 */
public class EntryLabel extends NodeLabel {

	public EntryLabel(ASTNode node) {
		super(node);
	}

	public String toString() {
		String nodeExpr = node.toString();

		if (node instanceof VariableDeclarationStatement) {
			nodeExpr = ((VariableDeclarationStatement) node).fragments().get(0).toString();
		}

		if(node instanceof ExpressionStatement){
			nodeExpr = ((ExpressionStatement) node).getExpression().toString();
		}

		if (node instanceof IfStatement) {
			nodeExpr = "if(" + ((IfStatement) node).getExpression().toString() + ")";
		}

		if (node instanceof WhileStatement) {
			nodeExpr = "while(" + ((WhileStatement) node).getExpression().toString() + ")";
		}

		if (node instanceof ForStatement) {
			nodeExpr = "for( " + ((ForStatement) node).getExpression() .toString() + ")";
		}

		if (node instanceof EnhancedForStatement) {
			nodeExpr = "for(" + ((EnhancedForStatement) node).getParameter() + ")";
		}

		if (node instanceof DoStatement) {
			nodeExpr = "do(" + ((DoStatement) node).getExpression()+ ")";
		}

		if (node instanceof MethodDeclaration) {
			nodeExpr = "init";
		}

		String ret = "entry[" + nodeExpr + "]";

		return ret;
	}
}
