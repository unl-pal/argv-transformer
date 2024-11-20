package transformer.regression.arrays;

import org.junit.BeforeClass;
import org.junit.Test;
//import org.testng.annotations.*;
import util.ArgVRandom;

public class ArrayTests {
	@BeforeClass
	public void setUp() {
		System.out.println("Testing with: TestNG and Bazel");
	}

	/// Doubles
	@Test
	public static int baseTestCase(int x) {
		double[] a = new double[5];
		a[0] = ArgVRandom.randomDouble();
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}

	@Test // (groups = { "unit" })
	public static int baseTestCase1(int x) {
		int[] a = new int[5];
		a[0] = ArgVRandom.randomInt();
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}

	@Test
	public static int baseTestCase2(int x) {
		int[] a = new int[ArgVRandom.randomInt()];
		a[0] = 5;
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}

	@Test
	public static void sort(int[] a) {
		final int N = a.length;
		for (int i = 1; i < N; i++) { // N branches
			int j = i - 1;
			int x = a[i];
			// First branch (j >= 0): 2 + 3 + ... + N = N(N+1)/2 - 1 branches
			// Second branch (a[j] > x): 1 + 2 + ... + N-1 = (N-1)N/2 branches
			while ((j >= 0) && (a[j] > x)) {
				a[j + 1] = a[j];
				j--;

				a[j + 1] = x;
			}
		}
	}

	@Test
	public static void testFunc() {
		int N = ArgVRandom.randomInt();
		int a[] = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = N - i;
		}

		try {
			sort(a); // TODO: figure out how to handle this getting removed
		} catch (Exception e) {
			assert false;
		}
	}
}
