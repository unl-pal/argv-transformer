package agent;

import Exploration.Exploration;
import agent.interfaces.IAbstractAgent;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.Map;

import model.AbstractMessage;
import model.BurningBuilding;
import model.AbstractMessage;

import rescuecore2.worldmodel.EntityID;
import rescuecore2.Constants;
import rescuecore2.config.Config;
import rescuecore2.log.Logger;
import rescuecore2.messages.Command;
import rescuecore2.standard.components.StandardAgent;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.Hydrant;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Refuge;
import rescuecore2.standard.entities.Road;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.standard.kernel.comms.ChannelCommunicationModel;
import rescuecore2.standard.kernel.comms.StandardCommunicationModel;
import rescuecore2.standard.messages.AKSay;
import rescuecore2.standard.messages.AKSpeak;
import rescuecore2.standard.messages.AKTell;
import util.Channel;
import util.MASLABBFSearch;
import util.MASLABPreProcessamento;
import util.MASLABRouting;
import util.MASLABSectoring;
import util.MSGType;

/**
 * Abstract base class for sample agents.
 *
 * @param <E> The subclass of StandardEntity this agent wants to control.
 */
public abstract class MASLABAbstractAgent<E extends StandardEntity> extends StandardAgent<E> implements IAbstractAgent {

    /**
     * Variaveis Sample Agent
     */
    private static final int RANDOM_WALK_LENGTH = 50;
    private static final String SAY_COMMUNICATION_MODEL = StandardCommunicationModel.class
            .getName();
    private static final String SPEAK_COMMUNICATION_MODEL = ChannelCommunicationModel.class
            .getName();
    /**
     * The search algorithm.
     */
    protected MASLABBFSearch search;
    /**
     * Whether to use AKSpeak messages or not.
     */
    protected boolean useSpeak;
    /**
     * Cache of building IDs.
     */
    protected List<EntityID> buildingIDs;
    /**
     * Cache of road IDs.
     */
    protected List<EntityID> roadIDs;
    /**
     * Cache of refuge IDs.
     */
    protected List<EntityID> refugeIDs;
    private Map<EntityID, Set<EntityID>> neighbours;
    /**
     *
     * Variaveis definidas por nós
     *
     */
    /**
     * The routing algorithms.
     */
    protected MASLABRouting routing;
    /**
     * Classe de setorização, responsável por pre-processar e carregar os arquivos.
     */
	protected MASLABSectoring sectoring;
	protected Exploration exploration;
	private static final String MAX_DISTANCE_KEY = "fire.extinguish.max-distance";
	private static final String MAX_VIEW_KEY = "perception.los.max-distance";
	protected List<BurningBuilding> IncendiosComunicar = new LinkedList<BurningBuilding>(); 

	protected int maxDistance;
	protected int maxView;
    protected int capacidadeCombate = 140;

	
    /**
     * Cache of Hydrant IDs.
     */
    protected List<EntityID> hydrantIDs;
    protected List<EntityID> roadIDsSetor1;
    protected List<EntityID> roadIDsSetor2;
    protected List<EntityID> roadIDsSetor3;
    protected List<EntityID> roadIDsSetor4;
    protected List<EntityID> roadIDsPrincipal;
    protected List<EntityID> Bloqueios;
	protected int PreProcessamento = 0;
	Random rand = new Random();
}
