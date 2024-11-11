/** filtered and transformed by ARG-V */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class Release2SymbolicDoubleDemo {
	/** ARG-V: suitable */
	 public static int demoMethod(int x, int y) {
		double var1 = Debug.makeSymbolicReal("x10");
		double var2 = 10.0 * Debug.makeSymbolicReal("x11");
		double result = var1 * var2;
		int z = x * y;
		if (result > z) {
			return x;
		} else {
			return y;
		}
	}
	
	/** ARG-V: suitable */
	 private static double helperFunction(int x, int y) {
		if (x > y) {
			return Debug.makeSymbolicReal("x12");
		} else {
			return 10.0;
		}
	}
}
