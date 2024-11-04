/** filtered and transformed by ARG-V */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicDoubleTest {
	/** ARG-V: suitable */
	 public static int splitter(double input1, double input2) {
		double value = (double) (Debug.makeSymbolicReal("x6") * 10.0);
		double time = (double) System.nanoTime();
		if (value > input2) {
			return -1;
		} else if (time > input1) {
			return 1;
		} else {
			return 0;
		}
	}

}
