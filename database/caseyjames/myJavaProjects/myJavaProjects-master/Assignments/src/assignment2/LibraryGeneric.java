package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Class representation of a library (a collection of library books).
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 1.0
 */
public class LibraryGeneric<Type> {

    private ArrayList<LibraryBookGeneric<Type>> library;

    /**
     * Comparator that defines an ordering among library books using the ISBN.
     */
    protected class OrderByIsbn implements Comparator<LibraryBookGeneric<Type>> {
    }

    /**
     * Comparator that defines an ordering among library books using the author,  and book title as a tie-breaker.
     */
    protected class OrderByAuthor implements Comparator<LibraryBookGeneric<Type>> {
    }

    /**
     * Comparator that defines an ordering among library books using the due date.
     */
    protected class OrderByDueDate implements Comparator<LibraryBookGeneric<Type>> {
    }

}

