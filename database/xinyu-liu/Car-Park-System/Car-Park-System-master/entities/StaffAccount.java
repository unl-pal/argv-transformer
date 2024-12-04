package carparksystem.entities;

import java.io.File;
import java.util.ArrayList;

/** 
 * This is an entity class storing the 
 * information of staff account.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class StaffAccount {
	private int id;
	private String name;
	private String campusCardNum;
	private String email;
	private String phoneNum;
	private File staffAccountFile = new File("staff_account.txt");
	private DBUtil dbUtil = new DBUtil(staffAccountFile);
}
