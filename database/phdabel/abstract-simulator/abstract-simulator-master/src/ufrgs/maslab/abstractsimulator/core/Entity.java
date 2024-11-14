package ufrgs.maslab.abstractsimulator.core;

import java.io.Serializable;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.util.IdGenerator;

public abstract class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <ul>
	 * <li>id of the entity</li>
	 * </ul>
	 */
	private int id;
	
		
	/**
	 * <ul>
	 * <li>used to roll dices for actions and events</li>
	 * <li>actions are probabilistic</li>
	 * <li>each entity has dices</li>
	 * </ul>
	 */
	private Random dices = new Random();

	

}
