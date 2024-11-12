package assignment7;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * Represents vertices for graphs.
 * 
 * @author Paymon Saebi
 */
public class Vertex implements Comparable<Vertex>
{
	private String name;
	private int inDegree;
	private Vertex cameFrom;
	private boolean isVisited;
	private int costFromStart;
	private LinkedList<Edge> adj;	
}