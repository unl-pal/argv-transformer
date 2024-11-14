package metadata;

// Java Imports
import java.awt.Container;
import java.util.HashMap;


// Custom Imports
import networking.request.GameRequest;

/**
 * The GameRequestTable class stores a mapping of unique request code numbers
 * with its corresponding request class.
 */
public class GameRequestTable {

    private static HashMap<Short, Class> requestNames; // Stores request classes by request codes
}
