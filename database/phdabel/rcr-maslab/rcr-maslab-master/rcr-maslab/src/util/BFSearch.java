package util;

import rescuecore2.worldmodel.EntityID;
import rescuecore2.worldmodel.Entity;
import rescuecore2.misc.collections.LazyMap;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.standard.entities.Area;

/**
 * A sample search class that uses a connection graph to look up neighbours.
 */
public final class BFSearch {
	private Map<EntityID, Set<EntityID>> graph;
}
