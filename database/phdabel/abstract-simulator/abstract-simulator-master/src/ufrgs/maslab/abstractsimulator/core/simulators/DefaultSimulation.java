package ufrgs.maslab.abstractsimulator.core.simulators;

import java.util.Random;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;

public abstract class DefaultSimulation {

	private Integer difficulty = 6;
	
	private Integer modifier;
	
	/**
	 * <ul>
	 * <li>used to roll dices for actions and events</li>
	 * <li>actions are probabilistic</li>
	 * <li>each entity has dices</li>
	 * </ul>
	 */
	private Random dices = new Random();
			
	
	
	
	
}
