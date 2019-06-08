package tests.Transformer.SPFExamples;

import java.util.ArrayList;

public class TestArray {
	public static void testBasic(int x) {
		int[] arr = new int[10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}

		System.out.println("Contents of arr: ");
		for (int i : arr) {
			System.out.print(i + " ");
		}
		System.out.println("\n");

		if (arr[x] == 3) {
			System.out.println("Found solution for arr[x] == 3: " + Debug.getSolvedPC());
		} else {
			System.out.println("Found solution for arr[x] != 3: " + Debug.getSolvedPC());
		}
	}

	public static void testArrayList(int x) {
		int arrSize = 1000;
		ArrayList<Integer> arrList = new ArrayList<>();
		for (int i = 0; i < arrSize; i++) {
			arrList.add(i);
		}

		System.out.println("Contents of arr: ");
		for (int i : arrList) {
			System.out.print(i + " ");
		}
		System.out.println("\n");

		if (arrList.get(x) == 999) {
			System.out.println("Found solution for arrList[x] == 999: " + Debug.getSolvedPC());
		} else {
			System.out.println("Found solution for arrList[x] != 999: " + Debug.getSolvedPC());
		}
	}

	public static void main(String[] args) {
		// testBasic(0);
		testArrayList(0);
	}
}
