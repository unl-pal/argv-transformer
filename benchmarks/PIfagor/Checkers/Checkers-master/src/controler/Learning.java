/** filtered and transformed by PAClab */
package controler;

import org.sosy_lab.sv_benchmarks.Verifier;

public class Learning {
	// part of learning
	/** PACLab: suitable */
	 public static void learn() throws Exception {
		boolean readFile = !CT.FIRST_STAGE_LEARNING;
		boolean writeFile = true;

		// inInint(temp,inFile,fileNamString);

		long start = Verifier.nondetInt();
		long end = Verifier.nondetInt();
		long time = (end - start) / 1000000;
		for (int i = 0; i < Verifier.nondetInt(); i++) {
			while (Verifier.nondetInt() == 0) {
			}

		}
		start = Verifier.nondetInt();
		// writing to file
		if (writeFile) {
		}

		end = Verifier.nondetInt();
		time = (end - start) / 1000000;
	}

}
