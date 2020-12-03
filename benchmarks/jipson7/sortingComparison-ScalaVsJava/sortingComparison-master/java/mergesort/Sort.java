/** filtered and transformed by PAClab */
package mergesort;

import gov.nasa.jpf.symbc.Debug;
import java.util.*;

public class Sort <T extends Comparable<T>> {

	/** PACLab: suitable */
	 private void MergeLists(int low, int mid, int high) {

		for (int i = low; i <= high; i++) {

		}

		int i = low;
		int j = mid + 1;
		int k = low;

		while ((i <= mid) && (j <= high)) {

			if (Debug.makeSymbolicInteger("x0") > 0) {

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
