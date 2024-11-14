/** filtered and transformed by PAClab */
package ufrgs.maslab.abstractsimulator.values;

import org.sosy_lab.sv_benchmarks.Verifier;

public class FireBuildingTask {

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
		if(this.getMatter() == Matter.WOODEN_HOUSE)
		{
		}else if(this.getMatter() == Matter.REINFORCED_CONCRETE){
		}else{
		}
	}
	
	/**
	 * generates random task values
	 * @return
	 */
	/** PACLab: suitable */
	 public static double[] randomTask(){
		double[] randomTask = new double[9];
		int apartmentsPerFloor = 0;
		int buildingHP = 0;
		int floors = 0;
		int groundArea = 0;
		double success = 0;
		
		double x = Verifier.nondetDouble();
		double y = Verifier.nondetDouble();
		int matter = Verifier.nondetInt();
		int temperature = Verifier.nondetInt();
		if(matter == Verifier.nondetInt())
		{
			floors = (1 + Verifier.nondetInt());
			groundArea = 50;
		}else if(matter == Verifier.nondetInt()){
			floors = (1 + Verifier.nondetInt());
			groundArea = (50 + Verifier.nondetInt());
		}else{
			floors = (1 + Verifier.nondetInt());
			groundArea = (50 + Verifier.nondetInt());
		}
		
		apartmentsPerFloor = (int)(groundArea / 50);
		
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
