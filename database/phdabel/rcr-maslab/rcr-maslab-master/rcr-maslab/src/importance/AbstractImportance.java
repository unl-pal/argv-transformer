package importance;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import agent.MASLABAbstractAgent;
import rescuecore2.standard.components.StandardAgent;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;
import util.MASLABSectoring;
import util.Setores;

/**
 * Abstract class that stores importance calculation stuff
 * @author anderson
 *
 */
public abstract class AbstractImportance {
	
	/**
	 * Reference to the agent who will calculate the sector importance
	 */
	protected MASLABAbstractAgent<?> caller;
	
	/**
	 * Maps a sector (Integer) - see util.Setores - to its
	 * importance (Integer)
	 */
	protected Map<Integer, Integer> sectorImportances;
}
