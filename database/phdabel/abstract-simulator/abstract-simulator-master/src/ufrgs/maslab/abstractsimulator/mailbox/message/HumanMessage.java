package ufrgs.maslab.abstractsimulator.mailbox.message;

public class HumanMessage extends AgentMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6957318464283032931L;
	/**
	 * physical attributes
	 */
	private int strength = 1;
	private int dexterity = 1;
	private int stamina = 1;
	
	/**
	 * social attributes
	 */
	private int charisma = 1;
	private int appearance = 1;
	private int leadership = 1;
	
	/**
	 * mental attributes
	 */
	private int intelligence = 1;
	private int reasoning = 1;
	private int perception = 1;
	
	
	/**
	 * other attributes
	 */
	private int will = 5;
	private int hp = 7;

}
