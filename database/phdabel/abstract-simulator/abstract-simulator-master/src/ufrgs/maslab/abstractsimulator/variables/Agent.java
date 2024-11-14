package ufrgs.maslab.abstractsimulator.variables;

import java.util.ArrayList;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.log.AgentLogger;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;

public class Agent extends Variable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Message> radio = new ArrayList<Message>();
	
	private ArrayList<Message> voice = new ArrayList<Message>();
	
	

}
