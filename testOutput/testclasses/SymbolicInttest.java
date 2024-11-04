/** filtered and transformed by ARG-V */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicInttest {
	/** ARG-V: suitable */
	 public static int splitter(int input1, int input2) {
		int value = (int) (Debug.makeSymbolicInteger("x12") * 10);
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}

}
