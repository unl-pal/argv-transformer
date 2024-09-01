package de.ftes.uon.seng2200.pa2;


/**
 * A distance attempt wraps a distance achieved by an athlete in a competition.
 * Apart from the {@link #distance} value, it holds the {@link #numberOfAttempt
 * number of the attempt} that the athlete achieved this distance on during the
 * competition.
 * 
 * A foul attempt is encoded as distance of {@code 0}, an attempt not yet made
 * is encoded as {@link Double#MIN_VALUE}.
 * 
 * @author Fredrik Teschke (3228760)
 */
public class DistanceAttempt implements Comparable<DistanceAttempt> {
	private final boolean hasBeenMade;
	private final int numberOfAttempt;
	private final double distance;
}
