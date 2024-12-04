package scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

/**
 * The main scheduler class that contains the scheduling algorithm
 * @author duncan
 *
 */

public class Scheduler {

	private int stress;
	private TGraph<TaskGroup> g;
	private List<Event> events;
	private ArrayList<Gap> gaps;
	
}
