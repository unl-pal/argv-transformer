package ufrgs.maslab.abstractsimulator.variables;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.interfaces.Platoon;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;


public class FireFighter extends Human implements Platoon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608281129356235256L;
	
	/**
	 * ability to extinguish fire from buildings
	 */
	private int fireFighting;

}
