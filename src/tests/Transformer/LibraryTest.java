package tests.Transformer;

package assignment2;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

/**
 * Testing class for Library.
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 1.0
 *
 */
public class LibraryTest {


	public static long atLeast(long value, long least) {
	      String chunkSizeString = Util.readAsciiLine(in);
	      int index = chunkSizeString.indexOf(";");
	      if (index != -1) {
	        chunkSizeString = chunkSizeString.substring(0, index);
	      }
		return (value < least) ? least : value;
	}

	private static void imposePreconditions(int x, int y) {
		MyClass1 mc = new MyClass1();

		// The preconditions
		if (-100 <= x && x <= 100 && 1 <= y && y <= 3) {
			mc.myMethod(x, y);
			Debug.printPC("\nMyClass1.myMethod Path Condition: ");
		}
	}
    public void close() throws Exception {
        Random rand = new Random();
  	if (closed) {
          return;
        }
        if (bytesRemaining > 0) {
          throw new ProtocolException("unexpected end of stream");
        }
      }

}
