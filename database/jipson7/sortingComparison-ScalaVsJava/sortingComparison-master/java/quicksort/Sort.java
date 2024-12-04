package quicksort;

import java.util.*;

public class Sort <T extends Comparable<T>> implements Comparator<T> {

	public ArrayList<T> list;

	private void QuickSort(int left, int right) {

		int i = left;
		int j = right;

		T pivot = list.get((right + left)/2);

		while (i <= j) {

			while (compare(list.get(i), pivot) > 0) {

				i++;

			}

			while (compare(list.get(j), pivot) < 0) {

				j--;

			}

			if (i <= j) {

				swap(i, j);

				i++;
				j--;

			}

		}

		if (left < j) {

			QuickSort(left, j);

		}

		if (i < right) {

			QuickSort(i, right);

		}
		
	}

}
