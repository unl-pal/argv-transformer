/** filtered and transformed by ARG-V */
 package transformer.regression.arrays;

import gov.nasa.jpf.symbc.Debug;

public class IntArraysSymbolicLengthTest2 {
	/** ARG-V: suitable */
	 public static void sort(int[] a) {
		// insertion sort
	    final int N = a.length;
	    for (int i = 1; i < N; i++) { // N-1 branches
	    	int j = i - 1; // size of sorted portion
	    	int x = a[i];
  // First branch (j >= 0):  2 + 3 + ... + N = N(N+1)/2 - 1 branches
  // Second branch (a[j] > x):  1 + 2 + ... + N-1 = (N-1)N/2 branches
	    	while ((j >= 0) && (a[j] > x)) {
	    		a[j + 1] = a[j];
	    		j--;
	    	}
	    	a[j + 1] = x;
	    }
	}

	/** ARG-V: suitable */
	 public static void testFunc() {
	    int N = Debug.makeSymbolicInteger("x3");
	    int a[] = new int[N];
	    for (int i = 0; i < N; i++) {
	    	a[i] = N - i; // range of a[i]: 1 to N (earlier elements are larger)
	    }

	    try {
	    } catch (Exception e) {
	    	assert false;
	    }
	}
}
