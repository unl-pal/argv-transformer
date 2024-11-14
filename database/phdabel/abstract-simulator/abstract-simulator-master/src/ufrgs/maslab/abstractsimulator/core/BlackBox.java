package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.simulators.DefaultSimulation;
import ufrgs.maslab.abstractsimulator.core.simulators.basic.CommunicationSimulation;
import ufrgs.maslab.abstractsimulator.core.simulators.basic.PerceptionSimulator;
import ufrgs.maslab.abstractsimulator.core.viewer.Viewer;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;

public class BlackBox {
	
	/**
	 *  <ul>
	 *  <li>configuration file</li>
	 *  <li>contains all configuration parameters of the simulation</li>
	 *  </ul>
	 *  
	 */
	private String configFileName = "config.properties";
	
	private String messageFileName = "message.properties";
	
	private String exceptionFileName = "exception.properties";
	/**
	 * environment variable
	 */
	private Environment<? extends Entity> env;
	
	
	/**
	 *  list of simulations
	 */
	private ArrayList<DefaultSimulation> simulation = new ArrayList<DefaultSimulation>();

	/**
	 *  initial variables (agents)
	 */
	//private int initialAgents = Transmitter.getIntConfigParameter(this.configFileName, "config.variables");
	
	/**
	 * initial values (tasks)
	 */
	//private int initialTasks = Transmitter.getIntConfigParameter(this.configFileName, "config.values");
	
	/**
	 *  total timesteps
	 */
	private int timesteps = Transmitter.getIntConfigParameter(this.configFileName, "config.timesteps");
	
	/**
	 * current time
	 */
	private int time = 0;

	

}
