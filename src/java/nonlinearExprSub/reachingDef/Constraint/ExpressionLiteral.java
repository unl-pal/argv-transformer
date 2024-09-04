package nonlinearExprSub.reachingDef.Constraint;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Will keep track of variables used in expression.
 */
public class ExpressionLiteral {

    private Expression node;
    private List<String> varsUsed;

    public ExpressionLiteral(Expression node) {
        this.node = node;
        varsUsed = new ArrayList<>();
    }

    public void setVarsUsed(List<String> varsUsed) {
        this.varsUsed = varsUsed;
    }

    public boolean involves(String var) {
        return varsUsed.contains(var);
    }

    public String toString() {
        return "(" + node + ")";
    }

    public Expression getNode() {
        return node;
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
}
