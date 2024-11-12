package lab4;

/**
 * Builds a RNG simply by wrapping the java.util.Random class.
 */
public class JavasRandomNumberGenerator implements RandomNumberGenerator 
{

  //Create a Java.util.Random object to do the actual "work" of this class.

  private java.util.Random random_generator_ = new java.util.Random();
}
