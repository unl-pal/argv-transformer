package nonlinearExprSub.reachingDef.Constraint.Term;

import nonlinearExprSub.reachingDef.Solving.DefinitionSet;

import java.util.Set;

/**
 * Subtracting definition from the entry term set
 */
public class SetDifference extends ConstraintTerm {

    private ConstraintTerm entryTerm;
    private DefinitionLiteral defWild;
    private String varKilled;

    public SetDifference(ConstraintTerm entryTerm, DefinitionLiteral defWild) {
        this.entryTerm = entryTerm;
        this.defWild = defWild;
        this.varKilled = defWild.getName();
    }

    public void setReachingDefSet(Set<String> variables){
        reachingDefSet = new DefinitionSet(variables);
        entryTerm.setReachingDefSet(variables);
    }

    public void updateDefinitionSet(DefinitionSet ds2) {
        entryTerm.updateDefinitionSet(ds2);

        DefinitionSet copy = new DefinitionSet(reachingDefSet.getVariables());

        for (String var : copy.getVariables()) {
            copy.put(var, ds2.get(var));
        }

        reachingDefSet = copy;

        reachingDefSet.killDefinitions(varKilled);

    }

    public DefinitionSet getReachingDefSet() {
        return reachingDefSet;
    }

    public ConstraintTerm getEntryTerm() {
        return entryTerm;
    }

    public DefinitionLiteral getDefWild() {
        return defWild;
    }

    @Override
    public String toString() {
        return entryTerm + "\\" + defWild;
    }

}

