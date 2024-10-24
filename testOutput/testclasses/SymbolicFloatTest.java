/** filtered and transformed by PAClab */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicFloatTest {
	/** PACLab: suitable */
	 public static int splitter(float input1, float input2) {
		float value = (float) (Debug.makeSymbolicReal("x7") * 10.0f);
		float baseCase = (float) Debug.makeSymbolicReal("x8");
		float parenthesizedTime = ((float) Debug.makeSymbolicReal("x9"));
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}

}
