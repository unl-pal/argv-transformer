/** filtered and transformed by ARG-V */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class MinimalSymbolicIntTestCase {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		int replaceTarget = Debug.makeSymbolicInteger("x3") * 10;
		if (x > replaceTarget) {
			return 1;
		} else {
			return 0;
		}
	}

}
