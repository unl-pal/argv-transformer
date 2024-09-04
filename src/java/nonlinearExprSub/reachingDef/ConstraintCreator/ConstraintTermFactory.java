package nonlinearExprSub.reachingDef.ConstraintCreator;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SimpleName;
import nonlinearExprSub.reachingDef.Constraint.Term.ConstraintTerm;
import nonlinearExprSub.reachingDef.Constraint.Term.DefinitionLiteral;
import nonlinearExprSub.reachingDef.Constraint.Term.EntryLabel;
import nonlinearExprSub.reachingDef.Constraint.Term.ExitLabel;

public class ConstraintTermFactory {

    private HashMap<ASTNode, ConstraintTerm> termMapEntry;
    private HashMap<ASTNode, ConstraintTerm> termMapExit;
    private HashMap<SimpleName, HashMap<ASTNode, DefinitionLiteral>> varMap;

    public ConstraintTermFactory() {
        termMapEntry = new HashMap<>();
        termMapExit = new HashMap<>();
        varMap = new LinkedHashMap<>();
    }


    public ConstraintTerm createEntryLabel(ASTNode node) {
        ConstraintTerm t = termMapEntry.get(node);
        if (t == null) {
            t = new EntryLabel((node));
            termMapEntry.put(node, t);
        }

        return t;
    }

    public void setEntryLabel(ASTNode node, ConstraintTerm term) {
        termMapEntry.put(node, term);
    }

    public boolean containsEntryLabel(ASTNode node) {
        ConstraintTerm t = termMapEntry.get(node);
        return (t != null);
    }

    public ConstraintTerm createExitLabel(ASTNode node) {
        ConstraintTerm t = termMapExit.get(node);
        if (t == null) {
            t = new ExitLabel(node);
            termMapExit.put(node, t);
        }
        return t;
    }

    public ConstraintTerm createDefinition(String name, ASTNode node) {
        DefinitionLiteral def = new DefinitionLiteral(name, node);
        return def;
    }

    public DefinitionLiteral createDefinitionWildcard(String name) {
        DefinitionLiteral defWild = new DefinitionLiteral(name);
        return defWild;
    }
}
