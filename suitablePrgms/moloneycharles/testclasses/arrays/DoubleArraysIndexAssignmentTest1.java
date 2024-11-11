package testclasses.arrays;

import util.ArgVRandom;

public class DoubleArraysIndexAssignmentTest1 {
	public static int baseTestCase(int x) {
		double[] a = new double[5];
		a[0] = ArgVRandom.randomDouble();
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
