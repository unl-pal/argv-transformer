/** filtered and transformed by ARG-V */
 package transformer.integration;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int baseTestCase(int x) {
		int replaceTarget = Verifier.nondetInt() * 10;
		if (x > replaceTarget) {
			return 1;
		} else {
			return 0;
		}
	}

}
