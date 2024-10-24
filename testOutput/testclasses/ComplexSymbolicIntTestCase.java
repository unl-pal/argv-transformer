/** filtered and transformed by PAClab */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class ComplexSymbolicIntTestCase {
	/** PACLab: suitable */
	 public static int variedTestCase(int x, int y, int z) {
		int v1 = (int) (Debug.makeSymbolicInteger("x0") * 5);
		int v4 = (int) (Debug.makeSymbolicInteger("x1") * 10) + y + helperFunctionOne(z);
		if (x > v1) {
			return helperFunctionOne(y);
		} else {
			return helperFunctionOne(v4);
		}
	}
	
	/** PACLab: suitable */
	 public static int helperFunctionOne(int a) {
		int v2 = (int) (Debug.makeSymbolicInteger("x2") * 2);
		int v3 = Debug.makeSymbolicInteger("x3") / 1000;
		if (a > v2 && a > v3) {
			return 1;
		} else if (a == v2 || a == v3) {
			return 2;
		} else {
			return a * 500;
		}
	}

}
