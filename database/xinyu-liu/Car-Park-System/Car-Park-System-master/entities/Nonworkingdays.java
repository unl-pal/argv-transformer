package carparksystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/** 
 * This is an entity class storing the information of 
 * non-working-day which are set by the administrator.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class Nonworkingdays {
	// MM-dd-yyyy
	private ArrayList<String> nonworkingdaylist;
	private File nonworkingdayFile = new File("nonworkingday.txt");
}
