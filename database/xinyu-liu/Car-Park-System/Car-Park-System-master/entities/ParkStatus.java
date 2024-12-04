package carparksystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/** 
 * This is an entity class storing the 
 * information of park status.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class ParkStatus {
	private int ticketNum = 100;
	private double coinNum = 0;
	private int spaceNum = 30;
	private File parkStatusFile = new File("park_status.txt");
}
