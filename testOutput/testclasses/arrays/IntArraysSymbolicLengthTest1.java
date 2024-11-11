/** filtered and transformed by ARG-V */
 package testclasses.arrays;

import gov.nasa.jpf.symbc.Debug;

public class IntArraysSymbolicLengthTest1 {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		int[] a = new int[Debug.makeSymbolicInteger("x2")];
		a[0] = 5;
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
