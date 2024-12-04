package networking.response;

// Java Imports
import java.lang.reflect.Field;

/**
 * The GameResponse class is an abstract class used as a basis for storing
 * response information.
 */
public abstract class GameResponse {

    protected byte[] responseInBytes; // Response information stored as bytes
    protected short responseCode;
}