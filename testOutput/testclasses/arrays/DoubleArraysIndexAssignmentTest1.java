/** filtered and transformed by ARG-V */
 package testclasses.arrays;

import gov.nasa.jpf.symbc.Debug;

public class DoubleArraysIndexAssignmentTest1 {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		double[] a = new double[5];
		a[0] = Debug.makeSymbolicReal("x0");
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
