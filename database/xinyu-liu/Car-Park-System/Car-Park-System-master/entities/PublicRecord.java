package carparksystem.entities;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/** 
 * This is an entity class storing the 
 * information of public record.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class PublicRecord extends Record {
	private String publicNum;
	private File publicRecordFile = new File("public_record.txt");
	private DBUtil dbUtil = new DBUtil(publicRecordFile);
}
