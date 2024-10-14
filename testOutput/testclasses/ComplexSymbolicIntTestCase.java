/** filtered and transformed by PAClab */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class ComplexSymbolicIntTestCase {
	/** PACLab: suitable */
	 public static int variedTestCase(int x, int y, int z) {
		int v1 = Debug.makeSymbolicInteger("x1");
		int v4 = Debug.makeSymbolicInteger("x3") + y + helperFunctionOne(z);
		if (x > v1) {
			return Debug.makeSymbolicInteger("x4");
		} else {
			return Debug.makeSymbolicInteger("x5");
		}
	}
	
	/** PACLab: suitable */
	 public static int helperFunctionOne(int a) {
		int v2 = Debug.makeSymbolicInteger("x7");
		int v3 = (int) Debug.makeSymbolicInteger("x8") / 1000;
		if (a > v2 && a > v3) {
			return 1;
		} else if (a == v2 || a == v3) {
			return 2;
		} else {
			return a * 500;
		}
	}

}
