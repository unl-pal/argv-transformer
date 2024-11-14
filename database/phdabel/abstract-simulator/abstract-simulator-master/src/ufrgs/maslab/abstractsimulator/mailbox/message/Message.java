package ufrgs.maslab.abstractsimulator.mailbox.message;


import java.io.Serializable;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public abstract class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -142739020941263551L;

	private int fromAgent;
	
	private int toAgent;
	
	private int content;
	
	private Class<? extends Entity> contentClass;
	
	private Class<? extends Agent> fromClass;
	
	private Class<? extends Agent> toClass;
	
	private MessageType type;
	
	private Boolean broadCast = true;
	
	

}
