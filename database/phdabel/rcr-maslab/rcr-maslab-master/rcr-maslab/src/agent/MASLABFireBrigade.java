package agent;

import agent.interfaces.IFireBrigade;
import static rescuecore2.misc.Handy.objectsToIDs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import model.BurningBuilding;
import model.AbstractMessage;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.messages.Command;
import rescuecore2.log.Logger;
import rescuecore2.standard.entities.Hydrant;
import rescuecore2.standard.entities.Road;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Refuge;
import rescuecore2.standard.entities.FireBrigade;
import util.Channel;
import util.MASLABPreProcessamento;
import util.MASLABSectoring;
import util.MSGType;

/**
 * A sample fire brigade agent.
 */
public class MASLABFireBrigade extends MASLABAbstractAgent<FireBrigade>
		implements IFireBrigade {

	/**
	 * 
	 * Variaveis Sample Agent
	 * 
	 */

	private static final String MAX_WATER_KEY = "fire.tank.maximum";
	private static final String MAX_POWER_KEY = "fire.extinguish.max-sum";
	private static final String GAS_STATION_RANGE = "ignition.gas_station.explosion.range";
	
	private int maxWater;
	private int maxPower;
	private int gasStationRange;
	
	/**
	 * 
	 * Variaveis definidas por nós
	 * 
	 */
	
	private enum Estados {
		Abastecendo, BuscandoRefugio, Explorando, Combatendo;
	}
	
	private Estados estadoAnterior;
	private Estados estado;
	private EntityID destino = null;
	private EntityID alvoAtual = null;
	private EntityID alvoAnterior = null;
	private BurningBuilding alvo = null;
	
	private List<EntityID> path = new LinkedList<EntityID>();
	
	private int setorAtual;
	
    private String MSG_SEPARATOR = "-";
    private String MSG_FIM = ",";
    private Boolean trocarCanal = false;
    
    private List<BurningBuilding> Alvos = new LinkedList<BurningBuilding>();
//    private List<BurningBuilding> AlvosComunicar = new LinkedList<BurningBuilding>();
    
    //uma lista de bombeiros 'mal comportados' em kobe (p/ debug)
    List<Integer> monitored;
	
    private Boolean alterarAlvo = true;
    
    private boolean debug = false;
    
    //armazena a ultima posicao deste agente
  	EntityID lastPosition;
    
    //indicador de 'estou bloqueado por quantos timesteps'?
  	int stuckFor;
}
