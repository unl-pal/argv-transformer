package agent;

import Exploration.Exploration;
import Exploration.ExplorationWithComunication;
import Exploration.WalkingInSector;
import agent.interfaces.IPoliceForce;

import java.io.EOFException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import model.AbstractMessage;
import model.BurningBuilding;
import firesimulator.world.Civilian;
import gis2.scenario.ClearAllFunction;
import rescuecore.objects.World;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.messages.Command;
import rescuecore2.log.Logger;
import rescuecore2.misc.geometry.GeometryTools2D;
import rescuecore2.misc.geometry.Point2D;
import rescuecore2.misc.geometry.Line2D;
import rescuecore2.misc.geometry.Vector2D;
import rescuecore2.misc.geometry.spatialindex.BBTree;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.Road;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.StandardWorldModel;
import util.Channel;
import util.MASLABBFSearch;
import util.MASLABRouting;
import util.MASLABSectoring;
import util.MSGType;
import util.Setores;

/**
 * A sample police force agent.
 */
public class MASLABPoliceForce extends MASLABAbstractAgent<PoliceForce>
		implements IPoliceForce {

	/**
	 * 
	 * Variaveis Sample Agent
	 * 
	 */
	private static final String DISTANCE_KEY = "clear.repair.distance";
	public static final String MAX_VIEW_KEY = "perception.los.max-distance";

	private int distance;
	private List<EntityID> pathtoclean = new ArrayList<EntityID>();;
	private StandardEntity node; // node objetivo
	private int Setor = 6;
	private int objetivoSetor = 5; // Variavel auxiliar que define o objetivo do
	private int PortaBloqueada = 0;
	private String MSG_SEPARATOR = "-";
	private String MSG_FIM = ",";
	private List<EntityID> ObrigacoesSoterramento = new ArrayList<EntityID>();; // lista
																				// de
																				// chamado
																				// do
																				// setor
	private List<EntityID> ComunicarIncendios = new ArrayList<EntityID>();
	private int controletempoParado=0;
	private int controletempoParadoTotal=0;
	private EntityID PosicaoPassada;
	private List<EntityID> MensageActivites = new ArrayList<EntityID>();
	private int ocupado = 0;
	/**
	 * 
	 * Variaveis definidas por nós
	 * 
	 */
	private int resultado;

}
