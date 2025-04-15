/** filtered and transformed by ARG-V */
 package transformer.integration.arrays;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		int[] a = new int[Verifier.nondetInt()];
		a[0] = 5;
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
