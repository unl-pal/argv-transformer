/* Item.java
 *
 * Abstract superclass for all items available for rent
 * from the library (books, magazines, videos, music, etc.).
 *
 * @author Matthew Scott Levan
 * @version 03/20/2015
 * @semester Spring 2015
 */

import java.util.*;
import java.io.*;

public abstract class Item {
    // variables common to all subclasses of Item
    // (all other variables created in subclasses)
    protected String title; // title
    protected String dueDate; // due date
    protected String itemType; // type of item (book, magazine, video ,etc.)
    protected boolean availability; // true if available, false if not
    protected int pages; // number of pages
    protected String author;
    protected String ISBN;
}
