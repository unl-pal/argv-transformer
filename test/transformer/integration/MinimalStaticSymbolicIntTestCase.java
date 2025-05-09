package transformer.integration;

import util.ArgVRandom;

public class MinimalStaticSymbolicIntTestCase {
	public static int baseTestCase(int x) {
		int replaceTarget = ArgVRandom.randomInt() * 10;
		if (x > replaceTarget) {
			return 1;
		} else {
			return 0;
		}
	}
}
