/** filtered and transformed by PAClab */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicDoubleTest {
	/** PACLab: suitable */
	 public static int splitter(double input1, double input2) {
		double value = (double) (Debug.makeSymbolicReal("x5") * 10.0);
		double time = Debug.makeSymbolicReal("x6");
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}

}
