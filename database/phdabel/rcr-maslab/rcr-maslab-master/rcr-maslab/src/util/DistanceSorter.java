package util;

import java.util.Comparator;

import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;

/**
 * A comparator that sorts entities by distance to a reference point.
 */
public class DistanceSorter implements Comparator<StandardEntity> {
	private StandardEntity reference;
	private StandardWorldModel world;
}
