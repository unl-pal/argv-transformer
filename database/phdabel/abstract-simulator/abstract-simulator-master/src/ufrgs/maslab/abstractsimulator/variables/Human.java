package ufrgs.maslab.abstractsimulator.variables;

import java.util.ArrayList;
import java.util.Arrays;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.log.HumanLogger;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;

public class Human extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8750788016563339824L;
	
	private String exceptionFileName = "exception.properties";
	
	private transient ArrayList<String> attributes = new ArrayList<String>(Arrays.asList("Physical","Social","Mental"));
	
	private ArrayList<String> priority = new ArrayList<String>();
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
	
	/**
	 * <ul>
	 * <li>ear of the human</li>
	 * <li>all messages comes here first</li>
	 * </ul>
	 */
	private ArrayList<Message> ear = new ArrayList<Message>();
	
	
	/**
	 * roll attributes limited by the points parameters
	 * 
	 * @param points
	 * @return
	 */
	private int[] rollAttributesValue(int points)
	{
		int[] attr = new int[]{0,0,0};

		while((attr[0]+attr[1]+attr[2]) != points)
		{
			//first attribute
			attr[0] = this.rollDices(5);
			//second attribute
			if(points - attr[0] >= 4){
				attr[1] = this.rollDices(5);
			}else{
				if(((points+1)-attr[1])<=0){
					attr[1] = 0;
				}else{
					attr[1] = this.rollDices((points+1) - attr[1]);
				}
			}
			//third attribute
			if((points-(attr[0]+attr[1])) >= 4)
			{
				attr[2] = this.rollDices(5);
			}else{
				if(((points+1) - (attr[0]+attr[1]))<=0)
				{
					attr[2] = 0;
				}else{
					attr[2] = this.rollDices((points+1) - (attr[0]+attr[1]));
				}
			}
		}
		return attr;
	}

	
	

}
