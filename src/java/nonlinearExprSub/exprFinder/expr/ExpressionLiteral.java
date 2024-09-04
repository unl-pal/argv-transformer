package nonlinearExprSub.exprFinder.expr;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of variables used in expression.
 */
public class ExpressionLiteral {

    private String expr;
    private List<String> varsUsed;

    public ExpressionLiteral(String expr) {
        this.expr = expr;
        varsUsed = new ArrayList<>();
    }

    public void setVarsUsed(List<String> varsUsed) {
        this.varsUsed = varsUsed;
    }

    public boolean involves(String var) {
        return varsUsed.contains(var);
    }

    public String getExpr() {
        return expr;
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
}
