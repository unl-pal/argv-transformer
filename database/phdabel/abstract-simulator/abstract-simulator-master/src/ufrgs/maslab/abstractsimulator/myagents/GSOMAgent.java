package ufrgs.maslab.abstractsimulator.myagents;


import java.util.LinkedList;
import java.util.Queue;

import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class GSOMAgent<T extends Task> extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2001863310226679311L;
	
	public boolean leader = false;
	
	public Queue<GSOMAgent<T>> agents = new LinkedList<GSOMAgent<T>>();
		
	public T tmpVariable = null;

}
