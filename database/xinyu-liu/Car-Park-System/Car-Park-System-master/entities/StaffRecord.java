package carparksystem.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/** 
 * This is an entity class storing the 
 * information of staff record.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class StaffRecord extends Record{
	private String campusCardNum;
	private File staffRecordFile = new File("staff_record.txt");
	private DBUtil dbUtil = new DBUtil(staffRecordFile);
}
