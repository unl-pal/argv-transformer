/** filtered and transformed by PAClab */
package ufrgs.maslab.abstractsimulator.variables;

import org.sosy_lab.sv_benchmarks.Verifier;

public class FireStation{

	/**
	 * main of the clustering phase
	 */
	/** PACLab: suitable */
	 public void clusteringMain(int time){
		
		if(time == 2){
        	//show2DMap(this.gsom.getNeuralNetwork());
			for(int p = 0; p < Verifier.nondetInt(); p++)
			{
				for(int k = 0; k < Verifier.nondetInt(); k++)
				{
				}
			}
			//show3DMap(this.gsom.getNeuralNetwork());
        	
        }
		
	}
	
	/**
	 * internal class to comparate values and to provide ordering of tasks
	 * @author abel
	 *
	 */
	public static class ValueComparator {
	}
	
}
