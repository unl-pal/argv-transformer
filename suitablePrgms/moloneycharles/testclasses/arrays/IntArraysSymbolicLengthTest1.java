package testclasses.arrays;

import util.ArgVRandom;

public class IntArraysSymbolicLengthTest1 {
	public static int baseTestCase(int x) {
		int[] a = new int[ArgVRandom.randomInt()];
		a[0] = 5;
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
