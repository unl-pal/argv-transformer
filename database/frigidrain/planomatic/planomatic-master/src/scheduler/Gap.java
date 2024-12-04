package scheduler;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * A time slot between two events where tasks can be scheduled 
 * @author duncan
 *
 */

public class Gap extends Slot {

	// calculate the starting time of each task
	private List<Task> tasks;
	private int durationLeft;
}