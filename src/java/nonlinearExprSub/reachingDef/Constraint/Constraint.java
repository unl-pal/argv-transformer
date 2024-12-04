package nonlinearExprSub.reachingDef.Constraint;

import nonlinearExprSub.reachingDef.Constraint.Term.ConstraintTerm;

/**
 * An edge in the constraint graph.
 */
public class Constraint {

    private ConstraintTerm lhs;
    private ConstraintTerm rhs;
    private SubsetOperator op;

    public Constraint(ConstraintTerm lhs, SubsetOperator op, ConstraintTerm rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    public ConstraintTerm getLhs() {
        return lhs;
    }

    public ConstraintTerm getRhs() {
        return rhs;
    }

    public SubsetOperator getOp() {
        return op;
    }

    public String toString() {
        return lhs + " " + op + " " + rhs;
    }

}
