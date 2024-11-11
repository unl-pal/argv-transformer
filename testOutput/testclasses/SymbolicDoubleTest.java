/** filtered and transformed by ARG-V */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicDoubleTest {
	/** ARG-V: suitable */
	 public static int splitter(double input1, double input2) {
		double value = (double) (Debug.makeSymbolicReal("x13") * 10.0);
		double time = (double) System.nanoTime();
		if (value > input2) {
			return -1;
		} else if (time > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/** ARG-V: suitable */
	 public static void main (String[] args) {
		// double fake = Fake.getDouble();
		double time = (double) System.nanoTime();
		double var2 = (Debug.makeSymbolicReal("x14") * 5);
	}

}
