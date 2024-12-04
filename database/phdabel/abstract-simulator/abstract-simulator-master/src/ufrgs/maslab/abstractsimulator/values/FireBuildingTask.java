package ufrgs.maslab.abstractsimulator.values;

import java.util.Arrays;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.constants.Damage;
import ufrgs.maslab.abstractsimulator.constants.Matter;
import ufrgs.maslab.abstractsimulator.constants.Temperature;
import ufrgs.maslab.abstractsimulator.log.FireBuildingTaskLogger;
import ufrgs.maslab.abstractsimulator.util.Transmitter;

public class FireBuildingTask extends Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6064463956442943641L;
	
	/**
	 *  <ul>
	 *  <li>configuration file</li>
	 *  <li>contains all configuration parameters of the simulation</li>
	 *  </ul>
	 *  
	 */
	private String configFileName = "config.properties";
	
	/**
	 * levels of burning
	 * 0 - not burning
	 * 1 - room or kitchen
	 * 2 - apartment
	 * 3 - one floor
	 * 4 - more than one floor
	 * 
	 * 
	 */
	private Temperature temperature;
	
	/**
	 *  each type of matter of the buildings influences how
	 *  fire will propagate
	 *  0 = 0,6
	 *  1 = 0,3
	 *  2 = 0,15
	 */
	private Matter matter;
	
	/**
	 *  number of floors of the building
	 */
	private int floors = 1;
	/**
	 *  if groundArea
	 */
	private int groundArea;
	
	/**
	 * damage type of fire building task
	 */
	private Damage damageType = Damage.SEVERE;
	
	/**
	 * total number of apartments per floor
	 */
	private int apartmentsPerFloor;

	/**
	 * total building hit points
	 * <br/>
	 * when hp is 0 building fire can not be extinguished
	 */
	private int buildingHP;
	
	private Integer success;

	/**
	 * <ul>
	 * <li>default constructor</li>
	 * <li>Attributes</li>
	 * <li>Temperature - {0 - 4}</li>
	 * <li>Matter - {0 -2}</li>
	 * <li>Floors - {1 - maximum floors}</li>
	 * <li>Damage - Severe (2)</li>
	 * <li>groundArea - maximum groundArea</li>
	 * <li>apartmentsPerFloor</li>
	 * <li>buildingHP</li>
	 * </ul>
	 */
	public FireBuildingTask(){
		super();
		this.setTemperature(Temperature.randomTemperature());
		this.setMatter(Matter.randomMatter());
		if(this.getMatter() == Matter.WOODEN_HOUSE)
		{
			this.setFloors(1+this.rollDices(2));
			this.setGroundArea(50);
		}else if(this.getMatter() == Matter.REINFORCED_CONCRETE){
			this.setFloors(1+this.rollDices(5));
			this.setGroundArea(50 + this.rollDices(Transmitter.getIntConfigParameter(this.configFileName, "maximum.groundArea")));
		}else{
			this.setFloors(1+this.rollDices(Transmitter.getIntConfigParameter(this.configFileName, "maximum.floors")));
			this.setGroundArea(50 + this.rollDices(Transmitter.getIntConfigParameter(this.configFileName, "maximum.groundArea")));
		}
		Random r = new Random();
		
		this.setX(r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.xpos")));		
		this.setY(r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.ypos")));
		this.configureTask();
	}
	
	/**
	 * generates random task values
	 * @return
	 */
	public static double[] randomTask(){
		double[] randomTask = new double[9];
		Arrays.fill(randomTask, 0d);
		Random r = new Random();
		
		int apartmentsPerFloor = 0;
		int buildingHP = 0;
		int floors = 0;
		int groundArea = 0;
		double success = 0;
		
		double x = r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.xpos"));
		double y = r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.ypos"));
		int matter = Matter.randomMatter().getValue();
		int temperature = Temperature.randomTemperature().getValue();
		if(matter == Matter.WOODEN_HOUSE.getValue())
		{
			floors = (1 + r.nextInt(2));
			groundArea = 50;
		}else if(matter == Matter.REINFORCED_CONCRETE.getValue()){
			floors = (1 + r.nextInt(5));
			groundArea = (50 + r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.groundArea")));
		}else{
			floors = (1 + r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.floors")));
			groundArea = (50 + r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.groundArea")));
		}
		
		apartmentsPerFloor = (int)(groundArea / 50);
		
		switch(temperature)
		{
		
			case 1:
				success = 5;
				break;
			case 2:
				success = ((int)(groundArea / 10));
				break;
			case 3:
				success = (apartmentsPerFloor * (int)(groundArea / 10));
				break;
			case 4:
				success = (24+(apartmentsPerFloor * (int)(groundArea / 10)));
				break;
			default:
				success = 0;
				break;
			
		}
		
		buildingHP = floors * apartmentsPerFloor + (10 ^ matter);
		
		randomTask[0] = apartmentsPerFloor;
		randomTask[1] = buildingHP;
		randomTask[2] = floors;
		randomTask[3] = groundArea;
		randomTask[4] = matter;
		randomTask[5] = success;
		randomTask[6] = x;
		randomTask[7] = y;
		randomTask[8] = temperature;
		
		return randomTask;
	}
	
	
}
