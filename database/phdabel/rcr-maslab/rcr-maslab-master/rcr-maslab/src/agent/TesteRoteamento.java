package agent;

import agent.interfaces.IFireBrigade;
import static rescuecore2.misc.Handy.objectsToIDs;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

import rescuecore2.worldmodel.EntityID;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.messages.Command;
import rescuecore2.log.Logger;
import rescuecore2.standard.entities.Road;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Refuge;
import rescuecore2.standard.entities.FireBrigade;
import util.Channel;
import util.DistanceSorter;
import util.MASLABPreProcessamento;
import util.MASLABSectoring;
import util.Setores;

/**
 * A sample fire brigade agent.
 */
public class TesteRoteamento extends MASLABAbstractAgent<FireBrigade> implements
		IFireBrigade {

	/**
	 * 
	 * Variaveis Sample Agent
	 * 
	 */

	private static final String MAX_WATER_KEY = "fire.tank.maximum";
	private static final String MAX_DISTANCE_KEY = "fire.extinguish.max-distance";
	private static final String MAX_POWER_KEY = "fire.extinguish.max-sum";
	private int maxWater;
	private int maxDistance;
	private int maxPower;
	
	private MASLABSectoring sectoring;

	/**
	 * 
	 * Variaveis definidas por nós
	 * 
	 */

	private List<EntityID> Bloqueios;
}
