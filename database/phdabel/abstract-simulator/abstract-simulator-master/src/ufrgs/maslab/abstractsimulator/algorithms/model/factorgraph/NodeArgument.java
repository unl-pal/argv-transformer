package ufrgs.maslab.abstractsimulator.algorithms.model.factorgraph;

import java.util.HashMap;

public class NodeArgument {
	
	Object value = null;

    /**
     * Static table to keep a unique instance of a given argument.
     */
    private static HashMap<Integer, NodeArgument> table = new HashMap<Integer, NodeArgument>();

}
