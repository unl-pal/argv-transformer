/** filtered and transformed by PAClab */
package quicksort;

import gov.nasa.jpf.symbc.Debug;
import java.util.*;

public class Sort <T extends Comparable<T>> {

	/** PACLab: suitable */
	 private void QuickSort(int left, int right) {

		int i = left;
		int j = right;

		while (i <= j) {

			while (Debug.makeSymbolicInteger("x0") > 0) {

				i++;

			}

			while (Debug.makeSymbolicInteger("x1") < 0) {

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
