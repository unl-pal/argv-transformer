package tests.Transformer;

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

    // sole constructor of this LibraryGeneric<Type> class
    public LibraryGeneric() {
        library = new ArrayList<LibraryBookGeneric<Type>>();
    }

    /**
     * Performs a SELECTION SORT on the input ArrayList.
     * 1. Find the smallest item in the list.
     * 2. Swap the smallest item with the first item in the list.
     * 3. Now let the list be the remaining unsorted portion
     * (second item to Nth item) and repeat steps 1, 2, and 3.
     */
    private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c) {
        for (int i = 0; i < list.size() - 1; i++) {
            int j, minIndex;
            for (j = i + 1, minIndex = i; j < list.size(); j++)
                if (c.compare(list.get(j), list.get(minIndex)) < 0)
                    minIndex = j;
            ListType temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }

    /**
     * Add the specified book to the library, assume no duplicates.
     *
     * @param isbn   --
     *               ISBN of the book to be added
     * @param author --
     *               author of the book to be added
     * @param title  --
     *               title of the book to be added
     */
    public void add(long isbn, String author, String title) {
        library.add(new LibraryBookGeneric<Type>(isbn, author, title));
    }

    /**
     * Add the list of library books to the library, assume no duplicates.
     *
     * @param list --
     *             list of library books to be added
     */
    public void addAll(ArrayList<LibraryBookGeneric<Type>> list) {
        library.addAll(list);
    }

    /**
     * Add books specified by the input file. One book per line with ISBN, author,
     * and title separated by tabs.
     * <p/>
     * If file does not exist or format is violated, do nothing.
     *
     * @param filename Name of the text file, must have file extension as well.
     */
    public void addAll(String filename) {
        ArrayList<LibraryBookGeneric<Type>> toBeAdded = new ArrayList<LibraryBookGeneric<Type>>();

        try {
            // fileIn scans the file 'filename'
            Scanner fileIn = new Scanner(new File(filename));
            int lineNum = 1;

            // pull the data from the file one line at a time
            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine();

                // second scanner to read each line separately
                Scanner lineIn = new Scanner(line);
                lineIn.useDelimiter("\t");

                // next three if statements throw exceptions if the format is wrong
                if (!lineIn.hasNextLong())
                    throw new ParseException("ISBN", lineNum);
                long isbn = lineIn.nextLong();

                if (!lineIn.hasNext())
                    throw new ParseException("Author", lineNum);
                String author = lineIn.next();

                if (!lineIn.hasNext())
                    throw new ParseException("Title", lineNum);
                String title = lineIn.next();

                // fields from 1 line used to create new library book and add it to the temp list
                toBeAdded.add(new LibraryBookGeneric<Type>(isbn, author, title));

                // line number is followed to give line# of exception error if it occurrs
                lineNum++;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage() + " Nothing added to the library.");
            return;
        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage()
                    + " formatted incorrectly at line " + e.getErrorOffset()
                    + ". Nothing added to the library.");
            return;
        } catch (IOException e) {
            System.err.println(e.getMessage() + "\t The Files directory path is incorrect!");
            return;
        }

        library.addAll(toBeAdded);
    }

    /**
     * Returns the holder of the library book with the specified ISBN.
     * <p/>
     * If no book with the specified ISBN is in the library, returns null.
     *
     * @param isbn --
     *             ISBN of the book to be looked up
     */
    public Type lookup(long isbn) {
        for (LibraryBookGeneric<Type> libraryBookGeneric : library)
            if (libraryBookGeneric.getIsbn() == isbn)
                return libraryBookGeneric.getHolder();
        return null;
    }

    /**
     * Returns the list of library books checked out to the specified holder.
     * <p/>
     * If the specified holder has no books checked out, returns an empty list.
     *
     * @param holder --
     *               holder whose checked out books are returned
     */
    public ArrayList<LibraryBookGeneric<Type>> lookup(Type holder) {
        // initialize list of books with specified holder
        ArrayList<LibraryBookGeneric<Type>> returnList = new ArrayList<LibraryBookGeneric<Type>>();

        // parse library and add all books with specified holder to return list
        for (LibraryBookGeneric<Type> libraryBookGeneric : library) {
            if (libraryBookGeneric.getHolder() == null)
                continue;
            if (libraryBookGeneric.getHolder().equals(holder))
                returnList.add(libraryBookGeneric);
        }

        // return the substantiated list of books
        return returnList;
    }

    /**
     * Sets the holder and due date of the library book with the specified ISBN.
     * <p/>
     * If no book with the specified ISBN is in the library, returns false.
     * <p/>
     * If the book with the specified ISBN is already checked out, returns false.
     * <p/>
     * Otherwise, returns true.
     *
     * @param isbn   --
     *               ISBN of the library book to be checked out
     * @param holder --
     *               new holder of the library book
     * @param month  --
     *               month of the new due date of the library book
     * @param day    --
     *               day of the new due date of the library book
     * @param year   --
     *               year of the new due date of the library book
     */
    public boolean checkout(long isbn, Type holder, int month, int day, int year) {
        for (LibraryBookGeneric<Type> libraryBookGeneric : library)
            if (libraryBookGeneric.getIsbn() == isbn) {

                // check if book is already checked out
                if (libraryBookGeneric.getHolder() != null)
                    return false;

                // since the book hasn't been checked out set holder and due date and return
                libraryBookGeneric.setHolder(holder);
                libraryBookGeneric.setDueDate(month, day, year);
                return true;
            }
        // if the book wasn't found return false
        return false;
    }

    /**
     * Unsets the holder and due date of the library book.
     * <p/>
     * If no book with the specified ISBN is in the library, returns false.
     * <p/>
     * If the book with the specified ISBN is already checked in, returns false.
     * <p/>
     * Otherwise, returns true.
     *
     * @param isbn --
     *             ISBN of the library book to be checked in
     */
    public boolean checkin(long isbn) {
        // parse entire library
        for (LibraryBookGeneric<Type> libraryBookGeneric : library)
            // either return false if book is checked in or check the book out and return true
            if (libraryBookGeneric.getIsbn() == isbn) {
                if (libraryBookGeneric.getHolder() == null)
                    return false;
                libraryBookGeneric.checkIn();
                return true;
            }
        // if the book wasn't found return false
        return false;
    }

    /**
     * Unsets the holder and due date for all library books checked out be the
     * specified holder.
     * <p/>
     * If no books with the specified holder are in the library, returns false;
     * <p/>
     * Otherwise, returns true.
     *
     * @param holder --
     *               holder of the library books to be checked in
     */
    public boolean checkin(Type holder) {
        // get list of all books under specified holder
        ArrayList<LibraryBookGeneric<Type>> genericBooks = this.lookup(holder);
        if (genericBooks.size() == 0)
            return false;
        // check in each book that shows up in the list.
        for (LibraryBookGeneric<Type> genericBook : genericBooks)
            genericBook.checkIn();
        return true;
    }

    /**
     * Test method which returns a copy of the list of books
     */
    protected ArrayList<LibraryBookGeneric<Type>> bookList() {
        return new ArrayList<LibraryBookGeneric<Type>>(library);
    }

    /**
     * Returns the list of library books, sorted by ISBN (smallest ISBN first).
     */
    public ArrayList<LibraryBookGeneric<Type>> getInventoryList() {
        ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
        libraryCopy.addAll(library);

        OrderByIsbn comparator = new OrderByIsbn();

        sort(libraryCopy, comparator);

        return libraryCopy;
    }

    /**
     * Returns the list of library books, sorted by author
     */
    public ArrayList<LibraryBookGeneric<Type>> getOrderedByAuthor() {
        ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
        libraryCopy.addAll(library);

        OrderByAuthor comparator = new OrderByAuthor();

        sort(libraryCopy, comparator);

        return libraryCopy;
    }

    /**
     * Returns the list of library books with due dates older than the input
     * date. The list is sorted by date (oldest first).
     * <p/>
     * If no library books are overdue, returns an empty list.
     */
    public ArrayList<LibraryBookGeneric<Type>> getOverdueList(int month, int day, int year) {
        ArrayList<LibraryBookGeneric<Type>> overdueList = new ArrayList<LibraryBookGeneric<Type>>();

        // calender  date determined by the parameters passed in of month, day, year
        GregorianCalendar selectDate = new GregorianCalendar(year, month, day);

        // only books that test to be overdue are added to the overdueList
        for (LibraryBookGeneric<Type> libBook : library) {
            // handle null case in due date (book checked in)
            if (libBook.getDueDate() == null)
                continue;

            // if the book is overdue add it to the list
            if ((libBook.getDueDate().compareTo(selectDate)) < 0)
                overdueList.add(libBook);
        }

        OrderByDueDate comparator = new OrderByDueDate();

        //sorts overdueList by the OrderByDueDate comparator function object
        sort(overdueList, comparator);

        // return books in overdueList after they are sorted.
        return overdueList;
    }

    /**
     * Comparator that defines an ordering among library books using the ISBN.
     */
    protected class OrderByIsbn implements Comparator<LibraryBookGeneric<Type>> {

        /**
         * Returns a negative value if lhs is smaller than rhs. Returns a positive
         * value if lhs is larger than rhs. Returns 0 if lhs and rhs are equal.
         */
        public int compare(LibraryBookGeneric<Type> lhs,
                           LibraryBookGeneric<Type> rhs) {
            return (int) (lhs.getIsbn() - rhs.getIsbn());
        }
    }

    /**
     * Comparator that defines an ordering among library books using the author,  and book title as a tie-breaker.
     */
    protected class OrderByAuthor implements Comparator<LibraryBookGeneric<Type>> {

        /**
         * Uses the compareTo String method
         * Returns a negative value if lhs is lexicographically smaller than rhs. Returns a positive
         * value if lhs is lexicographically larger than rhs. If lhs == rhs, than the titles of the books
         * are compared to each other.
         */
        public int compare(LibraryBookGeneric<Type> lhs, LibraryBookGeneric<Type> rhs) {
            if ((lhs.getAuthor().compareTo(rhs.getAuthor())) > 0)
                return 1;
            else if ((lhs.getAuthor().compareTo(rhs.getAuthor())) < 0)
                return -1;
            else
                return lhs.getTitle().compareTo(rhs.getTitle());
        }
    }

    /**
     * Comparator that defines an ordering among library books using the due date.
     */
    protected class OrderByDueDate implements Comparator<LibraryBookGeneric<Type>> {

        /**
         * Returns a negative value if lhs is smaller than rhs. Returns a positive
         * value if lhs is larger than rhs. Returns 0 if lhs and rhs are equal.
         */
        public int compare(LibraryBookGeneric<Type> lhs, LibraryBookGeneric<Type> rhs) {
            /*
            any due date that is null has the year field set to max for the local date
            variables to ensure that books with due dates are due before books with null
            due dates (meaning no due date and they are checked in)
            */
            // local variable dates to avoid null pointer exceptions
            GregorianCalendar lhsDate = new GregorianCalendar();
            GregorianCalendar rhsDate = new GregorianCalendar();

            if (lhs.getDueDate() == null)
                lhsDate.set(Calendar.YEAR, lhsDate.getActualMaximum(Calendar.YEAR));
            else
                lhsDate = (GregorianCalendar) lhs.getDueDate().clone(); //clone copy of lhs due date

            if (rhs.getDueDate() == null)
                rhsDate.set(Calendar.YEAR, rhsDate.getActualMaximum(Calendar.YEAR));
            else
                rhsDate = (GregorianCalendar) rhs.getDueDate().clone(); //clone copy of rhs due date

            // return comparison of newly assigned local date variables
            return lhsDate.compareTo(rhsDate);
        }
    }

}

