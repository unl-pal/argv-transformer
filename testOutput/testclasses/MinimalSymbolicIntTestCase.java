/** filtered and transformed by PAClab */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class MinimalSymbolicIntTestCase {
	/** PACLab: suitable */
	 public static int baseTestCase(int x) {
		int replaceTarget = Debug.makeSymbolicInteger("x4") * 10;
		if (x > replaceTarget) {
			return 1;
		} else {
			return 0;
		}
	}

}
