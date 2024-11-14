/** filtered and transformed by PAClab */
package mergesort;

import org.sosy_lab.sv_benchmarks.Verifier;

public class Sort <T extends Comparable<T>> {

	/** PACLab: suitable */
	 private void MergeLists(int low, int mid, int high) {

		for (int i = low; i <= high; i++) {

		}

		int i = low;
		int j = mid + 1;
		int k = low;

		while ((i <= mid) && (j <= high)) {

			if (Verifier.nondetInt() > 0) {

				i++;

			} else {

				j++;

			}

			k++;

		}

		while (i <= mid) {

			k++;
			i++;
		}

	}

}
