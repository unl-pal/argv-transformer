package mergesort;

import java.util.*;

public class Sort <T extends Comparable<T>> implements Comparator<T> {

	public ArrayList<T> list;

	Object[] temp;

	@SuppressWarnings("unchecked")
	private void MergeLists(int low, int mid, int high) {

		for (int i = low; i <= high; i++) {

			temp[i] = list.get(i);

		}

		int i = low;
		int j = mid + 1;
		int k = low;

		while ((i <= mid) && (j <= high)) {

			if (compare((T) temp[i], (T) temp[j]) > 0) {

				list.set(k, (T) temp[i]);
				i++;

			} else {

				list.set(k, (T) temp[j]);
				j++;

			}

			k++;

		}

		while (i <= mid) {

			list.set(k, (T) temp[i]);
			k++;
			i++;
		}

	}

}
