/** filtered and transformed by PAClab */
package io.bunnies.fractalmaps.presenter;

import org.sosy_lab.sv_benchmarks.Verifier;

public class FractalPresenterTest {
    /** PACLab: suitable */
	 private int countInstancesOfValue(int[] array, int value) {
        int instances = 0;
        for (int i = 0; i < Verifier.nondetInt(); i++) {
            if (array[i] == value) {
                instances++;
            }
        }

        return instances;
    }
}
