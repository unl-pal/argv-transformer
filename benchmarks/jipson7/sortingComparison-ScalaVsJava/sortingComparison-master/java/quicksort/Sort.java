/** filtered and transformed by PAClab */
package quicksort;

import org.sosy_lab.sv_benchmarks.Verifier;

public class Sort <T extends Comparable<T>> {

	/** PACLab: suitable */
	 private void QuickSort(int left, int right) {

		int i = left;
		int j = right;

		while (i <= j) {

			while (Verifier.nondetInt() > 0) {

				i++;

			}

			while (Verifier.nondetInt() < 0) {

				j--;

			}

			if (i <= j) {

				i++;
				j--;

			}

		}

		if (left < j) {

		}

		if (i < right) {

		}
		
	}

}
