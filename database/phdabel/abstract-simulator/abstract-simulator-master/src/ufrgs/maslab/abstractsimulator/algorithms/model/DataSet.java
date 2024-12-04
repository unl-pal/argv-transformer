package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.*;

import ufrgs.maslab.abstractsimulator.normalization.Normalizer;

public class DataSet
{

    double min_x;
    double max_x;

    double min_y;
    double max_y;

    double min_z;
    double max_z;
    
    public TreeMap<Integer,Point> points;
}
