package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.ArrayList;

public class Point
{
	private int id;
    private double x;
    private double y;
    private Double z = null;
    private Centroid cluster; // cluster number
    private ArrayList<Double> attributes = new ArrayList<Double>();
}
