/** filtered and transformed by ARG-V */
 package transformer.regression;

import gov.nasa.jpf.symbc.Debug;

public class MinimalSymbolicIntTestCase {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		int replaceTarget = Debug.makeSymbolicInteger("x7") * 10;
		if (x > replaceTarget) {
			return 1;
		} else {
			return 0;
		}
	}

}
