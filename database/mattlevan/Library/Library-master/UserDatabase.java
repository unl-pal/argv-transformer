/* CustomerDatabase.java
 *
 * Subclass for the customer database in the library
 * (extends Database superclass).
 *
 * @author Matthew Scott Levan
 * @version 03/20/2015
 * @semester Spring 2015
 */
 
import java.util.*;
import java.io.*;

public class UserDatabase extends Database {
    // file name for storing the customer database
    private String fileName = "user_database.txt";

    // Scanner object for customer keyboard input
    private Scanner input = new Scanner(System.in);
}
