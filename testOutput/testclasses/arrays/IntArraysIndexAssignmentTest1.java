/** filtered and transformed by ARG-V */
 package testclasses.arrays;

import gov.nasa.jpf.symbc.Debug;

public class IntArraysIndexAssignmentTest1 {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		int[] a = new int[5];
		a[0] = Debug.makeSymbolicInteger("x1");
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
