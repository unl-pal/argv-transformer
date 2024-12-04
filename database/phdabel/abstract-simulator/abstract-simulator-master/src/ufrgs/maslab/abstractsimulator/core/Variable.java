package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;
//import java.util.HashMap;


import java.util.Random;

import ufrgs.maslab.abstractsimulator.mailbox.MailBox;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.values.Task;

public abstract class Variable extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8643465426657959565L;
			
	/**
	 * <ul>
	 * <li>current value assigned to this variable</li>
	 * </ul>
	 */
	private Integer value = null;
	
	/**
	 * <ul>
	 * <li>if this variable is allocated to some value or not</li>
	 * </ul>
	 */
	private boolean allocated = false;
	
	/**
	 * <ul>
	 * <li>lifetime of the variable</li>
	 * </ul>
	 */
	private int time = 0;

	/**
	 * <ul>
	 * <li>domain of the variable</li>
	 * <li>map of values</li>
	 * <li>value is the key and class is the value</li>
	 * </ul>
	 */
	//private HashMap<Integer,Class<? extends Entity>> domain = new HashMap<Integer,Class<? extends Entity>>();
	private ArrayList<Entity> domain = new ArrayList<Entity>();
	
	private Integer x;
	
	private Integer y;
	
	private MailBox mail = new MailBox();
	

}
