/** filtered and transformed by ARG-V */
 package transformer.integration.arrays;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		int[] a = new int[5];
		a[0] = Verifier.nondetInt();
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
