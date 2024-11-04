/** filtered and transformed by ARG-V */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicFloatTest {
	/** ARG-V: suitable */
	 public static int splitter(float input1, float input2) {
		float value = (float) ((float) Debug.makeSymbolicReal("x10") * 10.0);
		float baseCase = (float) Debug.makeSymbolicReal("x11");
		float parenthesizedTime = ((float) System.nanoTime());
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}

}
