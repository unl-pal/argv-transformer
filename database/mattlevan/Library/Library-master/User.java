/* User.java
 *
 * Abstract superclass for all users (managers, customers) in the library.
 *
 * @author Matthew Scott Levan
 * @version 03/20/2015
 * @semester Spring 2015
 */

import java.util.*;
import java.io.*;

public class User {
    // variables common to customers and managers
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int itemsHeld;
    private boolean loggedIn;
    private boolean managerStatus;

    // instantiate Scanner for user keyboard input
    Scanner input = new Scanner(System.in);
}
