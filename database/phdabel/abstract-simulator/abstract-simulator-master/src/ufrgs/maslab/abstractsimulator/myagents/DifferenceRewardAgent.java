package ufrgs.maslab.abstractsimulator.myagents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class DifferenceRewardAgent<T extends Task> extends Agent{
	
	
	/**
	 *  ANT Reward Agent piece of code
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;

	/**
	 *  V_k
	 */
	public Map<T, Double> actionUtility = new HashMap<T, Double>();
	
	/**
	 *  P_k
	 */
	public Map<T, Double> actionProbability = new HashMap<T, Double>();
	
	public Double tau = 0.5;
	
	public Double alpha = 0.9;
	
	public Double neededResources = 4.0;


}
