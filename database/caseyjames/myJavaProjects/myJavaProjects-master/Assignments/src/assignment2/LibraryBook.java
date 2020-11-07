package assignment2;

import java.util.GregorianCalendar;

/**
 * Class representation of a book. The ISBN, author, and title can never change
 * once the book is created.
 * <p/>
 * Note that ISBNs are unique.
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 1.0
 */
public class LibraryBook extends Book {

    // two extra fields added in order to checkout a book
    private String holder;
    private GregorianCalendar dueDate;
}
