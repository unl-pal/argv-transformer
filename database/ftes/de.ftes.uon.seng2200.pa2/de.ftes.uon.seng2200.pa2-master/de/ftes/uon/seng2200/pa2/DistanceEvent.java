package de.ftes.uon.seng2200.pa2;



/**
 * A distance event holds all the attempts made by an athlete during a
 * competition along with some basic data about that athlete.
 * 
 * @author Fredrik Teschke (3228760)
 *
 */
public class DistanceEvent implements Comparable<DistanceEvent> {
	/**
	 * Redundant list structure (output also needs chronological order).
	 */
	private final ArrayList<DistanceAttempt> distances = new ArrayListImpl<>();

	/**
	 * always 3-letter code
	 */
	private final String country;

	private final String athleteName;

	private final int index;
}
