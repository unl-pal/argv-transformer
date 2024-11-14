package scheduler;

import java.util.LinkedList;

public class TaskGroup {

	public static final int WEIGHT_VALUE = 10;
	public static final int WEIGHT_URGENCY = 5;
	public static final int WEIGHT_TIMELINESS = -3;
	public static final int DEFAULT_URGENCY = 0;
	
	private int id;
	private String name;
	private String text;
	private int value;
	private int urgency;
	private int duration;
	private int difficulty;
	LinkedList<Task> chunks;
	
	/**
	 * Separates the task into chunks if the duration of the task is longer than the given threshold.
	 * The number of chunks is given by floor(duration / (threshold / 2))
	 * @param threshold
	 */
	private void createChunks(int threshold) {
		chunks = new LinkedList<Task>();
		if(getDuration() >= threshold) {
			int numChunks = (int)Math.floor((double)getDuration()/(threshold/2));
			int rem = (int)getDuration() % numChunks;
			for(int i = 0; i < numChunks; i++) {
				int add = (i < rem) ? 1 : 0;
				chunks.push(new Task(this, (int)getDuration()/numChunks + add, getDifficulty()));
			}
		} else {
			chunks.push(new Task(this, getDuration(), getDifficulty()));
		}
		System.out.format("Created %d chunks for %s\n", chunks.size(), getName());
	}
	
}
