package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;


public class BranchAndBound<Var extends Variable, Val extends Value> {
	
	//TODO review the algorithm
	
	/**
	 * Map<Variable, Value>
	 */
	public Map<Var, Val> partialAssignment = new HashMap<Var, Val>();

	/**
	 * map to associate classes of variables and values
	 */
	//private HashMap<Integer, Class<? extends Entity>> classAssociation = new HashMap<Integer, Class<? extends Entity>>();
	
	public Double upperBound = Double.MAX_VALUE;
	
	public Double lowerBound = 0d;
	
	public Double alpha = 0d;
	
	private Environment<Entity> env = null;
	
	/**
	 * list of variables
	 */
	public Queue<Var> x = new LinkedList<Var>();
	
	/**
	 * list of values
	 */
	public ArrayList<Entity> d = new ArrayList<Entity>();
	
	/**
	 * constraints map
	 * Map<Constraint<Variable, Value>,Double>
	 * 
	 */
	public Map<Constraint, Double> c = new HashMap<Constraint, Double>();
	
	/**
	 *  internal class - constraints
	 *  
	 */
	public static class Constraint {
		
		
		//variables
		private Integer i;
		private Integer j;
		//values
		private Integer a;
		private Integer b;
		private boolean binary = false;
		private Object[] keys = new Object[4];
		
		
	}
	
	
	

}
