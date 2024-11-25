package transformer.regression;

import util.ArgVRandom;

public class ComplexSymbolicIntTestCase {
	public static int variedTestCase(int x, int y, int z) {
		int v1 = (int) (ArgVRandom.randomInt() * 5);
		int v4 = (int) (ArgVRandom.randomInt() * 10) + y + Math.abs(5);
		if (x > v1) {
			return helperFunctionOne(y);
		} else {
			return helperFunctionOne(v4);
		}
	}
	
	public static int helperFunctionOne(int a) {
		int v2 = (int) (ArgVRandom.randomInt() * 2);
		int v3 = (int) Math.random() / 1000;
		if (a > v2 && a > v3) {
			return 1;
		} else if (a == v2 || a == v3) {
			return 2;
		} else {
			return a * 500;
		}
	}

}
