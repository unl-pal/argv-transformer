package testclasses;

import util.ArgVRandom;

public class Release2SymbolicDoubleDemo {
	public static int demoMethod(int x, int y) {
		double var1 = ArgVRandom.randomDouble();
		double var2 = 10.0 * helperFunction(x, y);
		double result = var1 * var2;
		int z = x * y;
		if (result > z) {
			return x;
		} else {
			return y;
		}
	}
	
	private static double helperFunction(int x, int y) {
		if (x > y) {
			return ArgVRandom.randomDouble();
		} else {
			return 10.0;
		}
	}
}
