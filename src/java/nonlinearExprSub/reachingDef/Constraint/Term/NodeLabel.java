package nonlinearExprSub.reachingDef.Constraint.Term;

import org.eclipse.jdt.core.dom.ASTNode;
import nonlinearExprSub.reachingDef.Solving.DefinitionSet;

import java.util.Set;

/**
 * A node in the Constraint graph.
 *
 *
 */
public class NodeLabel extends ConstraintTerm {
	protected ASTNode node;

	public NodeLabel(ASTNode node) {
		this.node = node;
	}

	public void setReachingDefSet(Set<String> variables){
		reachingDefSet = new DefinitionSet(variables);
	}

	public void updateDefinitionSet(DefinitionSet ds2) {
		reachingDefSet = ds2;
	}

	public DefinitionSet getReachingDefSet() {
		return reachingDefSet;
	}

}
