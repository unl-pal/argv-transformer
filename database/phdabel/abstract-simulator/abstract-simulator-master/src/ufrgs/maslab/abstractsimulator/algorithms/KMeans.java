package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import ufrgs.maslab.abstractsimulator.algorithms.model.Centroid;
import ufrgs.maslab.abstractsimulator.algorithms.model.Point;

public class KMeans extends Algorithm {

	int clusters = 2;
	int maxClusters = 0;
	Double betterCluster = Double.MAX_VALUE;
	double lastDistance = 0;
	public ArrayList<Double> weight = new ArrayList<Double>();
	
	//centroid points
	ArrayList<Centroid> centroids = new ArrayList<Centroid>();
	
	//centroid points with
	HashMap<Centroid, ArrayList<Point>> cluster = new HashMap<Centroid, ArrayList<Point>>();
	
	public ArrayList<Centroid> bestCentroids = new ArrayList<Centroid>();

}
