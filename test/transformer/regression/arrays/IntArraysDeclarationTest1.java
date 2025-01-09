package transformer.regression.arrays;

import util.ArgVRandom;

public class IntArraysDeclarationTest1 {

	static final int MY_NUM = 5;
	static int[] c = new int[MY_NUM];
	static int d[] = new int[MY_NUM];

	public static int baseTestCase(int x) {
		int[] a = new int[ArgVRandom.randomInt()];
		int b[] = new int[ArgVRandom.randomInt()];
		a[0] = 5;
		b[0] = 5;
		c[0] = 5;
		d[0] = 5;
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
