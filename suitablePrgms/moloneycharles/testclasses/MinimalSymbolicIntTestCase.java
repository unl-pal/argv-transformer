package testclasses;

import util.ArgVRandom;

public class MinimalSymbolicIntTestCase {
	public static int baseTestCase(int x) {
		int replaceTarget = ArgVRandom.randomInt() * 10;
		if (x > replaceTarget) {
			return 1;
		} else {
			return 0;
		}
	}

}
