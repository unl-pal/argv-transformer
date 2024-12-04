package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ufrgs.maslab.abstractsimulator.algorithms.model.*;

public abstract class Algorithm
{

    public final static double UNDEFINED = Double.POSITIVE_INFINITY;

    // DISTANCE_MANHATTAN, DISTANCE_EUCLIDIAN or DISTANCE_EUCLIDIAN_SQ
    // Euclidian Sq is the same as Euclidian, but does not take the square root,
    // but instead squares the epsilon parameter. Thus it is a lot faster.
    public final static int DISTANCE_METRIC = Calculations.DISTANCE_MANHATTAN;

    
    protected DataSet field;

    /**
     * Cluster tree.
     */
    class ClusterNode
    {
        /**
         * Children of this node.
         */
        List<ClusterNode> children = new ArrayList<ClusterNode>();

        /**
         * Points in this node.
         */
        List<Point> points = new ArrayList<Point>();

        /**
         * This node's parent.
         */
        ClusterNode parent;

        /**
         * The split point.
         */
        Point splitPoint;

        /**
         * Removed marker.
         */
        boolean removed = false;
    }

}