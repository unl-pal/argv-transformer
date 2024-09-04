package nonlinearExprSub.reachingDef.Constraint.Term;

import nonlinearExprSub.reachingDef.Solving.DefinitionSet;

import java.util.Set;

/**
 * Represents a node in the constraint graph.
 *
 */
public abstract class ConstraintTerm {

    // set of reaching definitions
    public DefinitionSet reachingDefSet;

    public interface TermProcessor {
        void processTerm(ConstraintTerm term);
    }

    public abstract DefinitionSet getReachingDefSet();

    public abstract void updateDefinitionSet(DefinitionSet ds2);

    public abstract void setReachingDefSet(Set<String> variables);

    public void processTerms(TermProcessor processor) {
        processor.processTerm(this);
    }
}
