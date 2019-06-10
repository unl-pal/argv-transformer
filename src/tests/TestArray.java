package tests;

import java.util.Map;

public class TestArray {
	private int[] days;
	private int[] years;
	private Map<String, Integer> months;

	private static int[] intArray(int from, int to) {
		int[] result = new int[to - from + 1];
		for (int i = from; i <= to; i++)
			result[i - from] = i;
		return result;
	}

	public TestArray() {
		days = intArray(1, 31);
		years = intArray(1900, 2100);

	}

	public int[] getDays() {
		return days;
	}

	public int[] getYears() {
		return years;
	}

	public Map<String, Integer> getMonths() {
		return months;
	}
}
