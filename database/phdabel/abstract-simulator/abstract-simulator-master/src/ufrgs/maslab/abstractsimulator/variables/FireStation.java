package ufrgs.maslab.abstractsimulator.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import ufrgs.maslab.abstractsimulator.algorithms.Algorithm;
import ufrgs.maslab.abstractsimulator.algorithms.KMeans;
import ufrgs.maslab.abstractsimulator.algorithms.model.DataSet;
import ufrgs.maslab.abstractsimulator.algorithms.model.Point;
import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.interfaces.Building;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.log.FireBuildingTaskLogger;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.normalization.Template;
import ufrgs.maslab.abstractsimulator.normalization.TemplateNormalizer;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.values.Task;

public class FireStation extends Agent implements Building{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2877720837521565204L;

	/**
	 * custom fields and methods
	 */
	
	private HashMap<Integer, FireBuildingTaskMessage> tasks = new HashMap<Integer, FireBuildingTaskMessage>();
		
	private final String FILEKMEANS = "kmeans_info";
	
	private Template templateFireTask = null;
	
	private DataSet field = new DataSet();
	
	Algorithm kmeans = new KMeans();
	
	/**
	 * main of the clustering phase
	 */
	public void clusteringMain(int time){
		
		WriteFile.getInstance().openFile(this.FILEKMEANS);
		
        if(time == 2){
        	String header = "";
            header = "x;y;difficulty";
        	WriteFile.getInstance().write(header,this.FILEKMEANS);
        	ArrayList<Double> weights = new ArrayList<Double>(Arrays.asList(0.15, 0.15, 0.7));
        	((KMeans)kmeans).weight = weights;
        	kmeans.setField(this.field);
			kmeans.run();
			

			//show2DMap(this.gsom.getNeuralNetwork());
			for(int p = 0; p < ((KMeans)this.kmeans).bestCentroids.size(); p++)
			{
				switch(p)
            	{
            		case 0:
            			System.out.println("Centroid Black");
            			break;
            		case 1:
            			System.out.println("Centroid Green");
            			break;
            		case 2:
            			System.out.println("Centroid Blue");
            			break;
            		case 3:
            			System.out.println("Centroid Red");
            			break;
            		case 4:
            			System.out.println("Centroid Yellow");
            			break;
            		case 5:
            			System.out.println("Centroid Gray");
            			break;
            		case 6:
            			System.out.println("Centroid White");
            			break;
            		case 7:
            			System.out.println("Centroid Magenta");
            			break;
            		default:
            			System.out.println("Centroid Cyan");
            			break;
            			
            	}
				for(int k = 0; k < this.templateFireTask.getMaxInput().length; k++)
				{
					System.out.println(this.templateFireTask.getAttributes()[k]+" --> "+(this.templateFireTask.getMaxInput()[k]*((KMeans)this.kmeans).bestCentroids.get(p).getAttributes().get(k)));
				}
				System.out.println();
			}
			//show3DMap(this.gsom.getNeuralNetwork());
        	
        }
		
	}
	
	/**
	 * internal class to comparate values and to provide ordering of tasks
	 * @author abel
	 *
	 */
	public static class ValueComparator implements Comparator<Task> {

	    Map<Task, Double> base;
	}
	
}
