package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.variables.Human;


public abstract class Value extends Entity implements Comparable<Value> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5767460036572644735L;
	
	private int x;
	
	private int y;
	
	private ArrayList<Class<Human>> performer = new ArrayList<Class<Human>>();
	
	/**
	 * <ul>
	 * <li>double value used to optimization</li>
	 * </ul>
	 */
	private Double value;


}
