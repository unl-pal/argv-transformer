package travelling.salesman.ant.colony.optimization;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by arpit on 17/4/15.
 */
public class ACO_Algo {

    private double initialTrail = 1.0;

    private double alpha = 1;
    private double beta = 5;
    private double evaporation = 0.5;

    private double Q = 500; //trail deposit

    private double probabilityOfSelection = 0.01;

    public int numberOfTowns;
    public int antsCount = 30;

    private double graph[][] = null;
    private double trails[][] = null;

    private ArrayList<Ant> ants = null;

    //new random
    private Random rand = new Random();
    private ArrayList<Double> probsOfTrails;

    private int currentIndex = 0;

    public ArrayList<Integer> bestTour;
    public double bestTourLength;

    ArrayList<City_ACO> list_cities;

}
