package de.ftes.uon.seng2200.pa2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * An event holds all {@link DistaneEvent}s of all athletes for a competition.
 * 
 * @author Fredrik Teschke (3228760)
 *
 */
public class Event {
	public static final int NUMBER_OF_ATTEMPTS = 6;
	
	private static final String COMMA_AND_WHITESPACE_REGEX = ",\\s*";
	private static final String[] MEDALS = { "GOLD", "SILVER", "BRONZE" };

	@SuppressWarnings("unused")
	private final String eventName;
	private final int numberOfAthletes;
	private final ArrayList<DistanceEvent> distanceEvents;
}
