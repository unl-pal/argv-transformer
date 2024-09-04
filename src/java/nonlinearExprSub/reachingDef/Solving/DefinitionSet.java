package nonlinearExprSub.reachingDef.Solving;

import nonlinearExprSub.reachingDef.Constraint.Term.DefinitionLiteral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DefinitionSet {
    private HashMap<String, List<DefinitionLiteral>> varMap;
    private Set<String> variables;

    public DefinitionSet(Set<String> variables, DefinitionLiteral def) {
        this.variables = variables;
        varMap = new HashMap<>();
        initializeDefinitions();
        String var = def.getName();
        List<DefinitionLiteral> definitions = new ArrayList<>();
        definitions.add(def);
        varMap.put(var, definitions);
    }

    public DefinitionSet(Set<String> variables) {
        this.variables = variables;
        varMap = new HashMap<>();
        initializeDefinitions();
    }


    private void initializeDefinitions() {
        for (String var : variables) {
            List<DefinitionLiteral> definitions = new ArrayList<>();
            varMap.put(var, definitions);
        }
    }


    public boolean containsAll(DefinitionSet ds2) {
        for (String var : ds2.getVariables()) {
            List<DefinitionLiteral> l1 = varMap.get(var);
            List<DefinitionLiteral> l2 = ds2.getVarMap().get(var);

            if (l1 == null) {
                return false;
            }
            for (DefinitionLiteral l : l2) {
                if (!l1.contains(l)) {
                    return false;
                }
            }
        }
        return true;
    }

    public DefinitionSet unionWith(DefinitionSet ds2) {
        DefinitionSet ret = new DefinitionSet(variables);

        // copy whatever is in original definition set
        for (String var : this.getVariables()) {
            List<DefinitionLiteral> l = new ArrayList<>();
            ret.getVarMap().put(var, l);
            for (DefinitionLiteral def : get(var)) {
                l.add(def);
            }
        }

        // add whatever is in the new definition set
        for (String var : ds2.getVariables()) {
            List<DefinitionLiteral> l1 = ret.get(var);
            if (l1 == null) {
                l1 = new ArrayList<>();
                ret.getVarMap().put(var, l1);
            }
            List<DefinitionLiteral> l2 = ds2.getVarMap().get(var);
            for (DefinitionLiteral def : l2) {
                if (!l1.contains(def)) {
                    l1.add(def);
                }
            }
        }
        return ret;
    }

    public void killDefinitions(String var) {
        varMap.put(var, new ArrayList<>());
    }

    public List<DefinitionLiteral> get(String var) {
        return varMap.get(var);
    }

    public void put(String var, List<DefinitionLiteral> definitionLiterals) {
        varMap.put(var, definitionLiterals);
    }

    public HashMap<String, List<DefinitionLiteral>> getVarMap() {
        return varMap;
    }

    public Set<String> getVariables() {
        return varMap.keySet();
    }

    @Override
    public String toString() {
        String ret = "[";
        for (String name : varMap.keySet()) {
            ret += name + ": " + varMap.get(name) + " ";
        }
        ret += "]";
        return ret;
    }

}
