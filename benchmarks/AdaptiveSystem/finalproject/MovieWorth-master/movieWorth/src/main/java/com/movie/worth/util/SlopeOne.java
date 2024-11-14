/** filtered and transformed by PAClab */
package com.movie.worth.util;

import org.sosy_lab.sv_benchmarks.Verifier;

public class SlopeOne {

    /*
     * Function buildDiffMatrix()
     * Calculates the DiffMatrix for all items
     *
     */
    /** PACLab: suitable */
	 public void buildDiffMatrix() {

        int mFreq = Verifier.nondetInt();
		int maxItemsId = Verifier.nondetInt();
		float mteste = Verifier.nondetFloat();
		mteste = new float[maxItemsId + 1][maxItemsId + 1];
        mFreq = new int[maxItemsId + 1][maxItemsId + 1];

        for (int i = 1; i <= maxItemsId; i++) {
            for (int j = 1; j <= maxItemsId; j++) {
            }
        }

        /*  Calculate the averages (diff/freqs) */
        for (int i = 1; i <= maxItemsId; i++) {
            for (int j = i; j <= maxItemsId; j++) {
                if (Verifier.nondetInt() > 0) {
                }
            }
        }
    }

    class FloatEntryComparator{
    }

    public void Predict() {

        int maxItem = Verifier.nondetInt();
		float totalFreq[] = new float[maxItem + 1];

        /* Start prediction */
        for (int j = 1; j <= maxItem; j++) {
            totalFreq[j] = 0;
        }

        slopeoneresult = new int[5];
        int q = 0;

    }
}
