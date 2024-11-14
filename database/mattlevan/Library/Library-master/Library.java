/* Library.java
 *
 * Driver class for the library management system.
 *
 * @author Matthew Scott Levan
 * @version 03/20/2015
 * @semester Spring 2015
 */

import java.util.*;
import java.io.*;

public class Library {
    protected static User user = new User();
    protected static Book book = new Book();
    protected static UserDatabase userDatabase = new UserDatabase();
    protected static ItemDatabase itemDatabase = new ItemDatabase();

    protected static Character userChar = null;
}
