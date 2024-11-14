package ufrgs.maslab.abstractsimulator.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ufrgs.maslab.abstractsimulator.log.EnvironmentLogger;
import ufrgs.maslab.abstractsimulator.log.FireBuildingTaskLogger;
import ufrgs.maslab.abstractsimulator.log.HumanLogger;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class Environment<E extends Entity> extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;
	
	
	/**
	 * 
	 * <ul>
	 * <li>value class</li>
	 * </ul>
	 * 
	 */
	private Map<Class<Value>,Integer> valClass = new HashMap<Class<Value>, Integer>();
	
	/**
	 * <ul>
	 * <li>variable class</li>
	 * </ul>
	 * 
	 */
	private HashMap<Class<Variable>, Integer> varClass = new HashMap<Class<Variable>,Integer>();
	
	/**
	 * <ul>
	 * <li>Set of variables of the environment</li>
	 * </ul>
	 * 
	 */
	private ArrayList<Variable> variableSet = new ArrayList<Variable>();
	
	/**
	 * <ul>
	 * <li>Set of values of the environment</li>
	 * </ul>
	 */
	private ArrayList<Value> valueSet = new ArrayList<Value>();
	
	/**
	 *  <ul>
	 *  <li>Map of allocation of variables and values in a given turn<li>
	 *  <li>Map Structure <Value Id, ArrayList<Variable Id>></li>
	 *  <ul>
	 */
	private Map<Integer, ArrayList<Integer>> allocationSet = new HashMap<Integer, ArrayList<Integer>>();
	
	/**
	 * <ul>
	 * <li>Id of the solved values</li>
	 * </ul>
	 */
	private ArrayList<Integer> solvedValues = new ArrayList<Integer>();
	
	/**
	 * <ul>
	 * <li>Id of unsolved values</li>
	 * <li>Values are unsolved if they reach the deadline</li>
	 * </ul>
	 */
	private ArrayList<Integer> removedValues = new ArrayList<Integer>();
	
	
	/*
	protected Class<? extends Value> getValClass() {
		return valClass;
	}

	protected void setValClass(Class<? extends Value> valClass) {
		this.valClass = valClass;
	}

	protected Class<? extends Variable> getVarClass() {
		return varClass;
	}

	protected void setVarClass(Class<? extends Variable> varClass) {
		this.varClass = varClass;
	}*/


}
