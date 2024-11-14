package carparksystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/** 
 * This is an entity class storing the 
 * information of administrator.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class Admin {
	private String username = "admin";
	private String password = "admin";
	private File adminFile = new File("admin.txt");
}
