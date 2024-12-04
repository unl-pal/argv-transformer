package ufrgs.maslab.abstractsimulator.mailbox.message;

import ufrgs.maslab.abstractsimulator.constants.Matter;
import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.constants.Temperature;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class FireBuildingTaskMessage extends TaskMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2474944316694077076L;

	private Temperature temperature;
	
	private Matter matter;
	
	private int floors;
	
	private int groundArea;
	
	private int apartmentsPerFloor;
	
	private int buildingHP;
	

}
