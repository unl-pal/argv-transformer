/** filtered and transformed by ARG-V */
 package transformer.integration.arrays;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		double[] a = new double[5];
		a[0] = Verifier.nondetDouble();
		if (x > a[0]) {
			return 1;
		} else {
			return 0;
		}
	}
}
