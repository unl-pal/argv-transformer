package agent;

import Exploration.WalkingInSector;
import agent.interfaces.IAmbulanceTeam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.AbstractMessage;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.messages.Command;
import rescuecore2.config.NoSuchConfigOptionException;
import rescuecore2.log.Logger;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.Refuge;
import util.Channel;
import util.DistanceSorter;
import util.MASLABBFSearch;
import util.MSGType;
import util.Setores;

/**
 * Agente ambulancia do time EPICENTER
 */
public class MASLABAmbulanceTeam extends MASLABAbstractAgent<AmbulanceTeam>
		implements IAmbulanceTeam {

	/**
	 * 
	 * Variaveis Sample Agent
	 * 
	 */
	private Collection<EntityID> unexploredBuildings;

	/**
	 * 
	 * Variaveis definidas por nós
	 * 
	 */
	
	public final static String MEAN_BURIEDNESS_DMG_KEY = "misc.damage.mean";
	public final static String STD_BURIEDNESS_DMG_KEY = "misc.damage.sd";
	public final static String FIRE_DMG_KEY = "misc.damage.fire";
	
	private static final int RANDOM_WALK_LENGTH = 50;
	private Map<EntityID, Set<EntityID>> neighbours; //para randomwalk
	
	//exibir prints?
	private final static boolean DEBUG = false;
	
	//fatores de dano devido a buriedness e fogo
	private float buriedness_dmg_factor;
	private int fire_damage;
	
	//memoria do agente, mapeia ID para um objeto q armazena os dados do humano
	private HashMap<EntityID, MemoryEntry> buriedness_memory;
	
	//timestep atual
	private int current_time;
	
	//alvo atual
	private EntityID current_target;

	//armazena a ultima posicao deste agente
	EntityID lastPosition;
	
	//indicador de 'estou bloqueado por quantos timesteps'?
	int stuckFor;
	
	//Setor do agente (melhora exploracao?)
	private int Setor = 0;
	private StandardEntity node; // node objetivo
	
	protected SampleSearch sampleSearch;

	/**
	 * Item da memoria da ambulancia.
	 * Contem a posicao, timestep esperado da morte e buriedness da vitima
	 * 
	 * @author Gabriel
	 *
	 */
	private class MemoryEntry {
		public EntityID position;
		public int expectedDeathTime;
		public int buriedness;
	}
	
	/*
	 * 
	 * Métodos Acessores se necessário
	 */
}
