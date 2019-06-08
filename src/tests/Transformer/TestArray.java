package tests.Transformer;


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

	}

	public static void testArrayList(int x) {
		int arrSize = 1000;
		for (int i = 0; i < arrSize; i++) {
		}

	}

	public static void main(String[] args) {
		// testBasic(0);
		testArrayList(0);
	}
}
