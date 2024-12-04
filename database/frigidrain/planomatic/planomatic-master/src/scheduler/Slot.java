package scheduler;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A time duration.
 * @author duncan
 *
 */

public abstract class Slot {
	
	private static final long minOffset = 60 * 1000;
	public static final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm a");
	public static final int DEFAULT_DIFFICULTY = 5;
	
	private int difficulty;
	private DateTime startTime;	// = minuteWise.parse( startTest );
	private int duration;
}