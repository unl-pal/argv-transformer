package util;

import rescuecore2.worldmodel.EntityID;
import rescuecore2.worldmodel.Entity;
import rescuecore2.misc.WorkerThread;
import rescuecore2.misc.collections.LazyMap;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

//import org.hamcrest.core.IsNull;

import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.standard.entities.Area;

/**
 * Classe que executa o algoritmo de roteamento.
 */
public final class MASLABBFSearch {

	private Map<EntityID, Set<EntityID>> graph;
	private StandardWorldModel model;
	
}
