package assignment7;

import java.util.HashMap;

/**
 * Represents a graph structure (a set of vertices each with a set of edges).
 * 
 * @author Paymon Saebi
 */
public class Graph
{
	/**
	 * The graph underlying structure is a HashMap
	 * Holds a set of vertices (String name mapped to Vertex instance) 
	 */
	
	private HashMap<String, Vertex> vertices; 
	private boolean isDirected, isWeighted;		
}
