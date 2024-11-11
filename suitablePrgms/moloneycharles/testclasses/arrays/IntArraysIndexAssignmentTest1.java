package testclasses.arrays;

import util.ArgVRandom;

public class IntArraysIndexAssignmentTest1 {
	public static int baseTestCase(int x) {
		int[] a = new int[5];
		a[0] = ArgVRandom.randomInt();
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
