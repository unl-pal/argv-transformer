package assignment9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Represents an arbitrary task management infrastructure
 *
 * @author Paymon saebi
 * @author Casey Nordgran
 * @author Cody Cortello
 * @version 7/16/2014
 */
public class TaskManager {
    private Task currentTask;
    private DeviceManager comms;
    private PriorityQueueHEAP<Task> PQ;

    /**
     * Represents the information to define a system task
     * Each task has name, a priority group A-Z, and a priority level 1-100
     * Lower values on group and level denote a higher priority
     *
     * @author Paymon Saebi
     */
    public class Task {
        private String taskName;
        private char priorityGroup;
        private int priorityLevel;
    }

    /**
     * Comparator class for TaskManager that compares objects of type Task.
     * Implements the compare method.
     */
    protected class TaskComparator implements Comparator<Task> {

    }
}
