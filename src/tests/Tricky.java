package tests;

public class Tricky {
	static String bar(int n, int k, String op) {
		if (k == 0)
			return "";
		return op + n + "]" + bar(n - 1, k - 1, op) + " ";
	}

	static String foo(int n) {
		String b = "";
		if (n < 2)
			b += "(";
		for (int i = 0; i < n; i++)
			b += "(";
		String s = bar(n - 1, n / 2 - 1, "*").trim();
		String t = bar(n - n / 2, n - (n / 2 - 1), "+").trim();
		// String s = bar(n-1,n-1,"*").trim();
		// String t = bar(n-2,n-2,"+").trim();
		// return b.toString()+n+(s+t).replace(']',')');
		return b + n + (s + t);
	}

	public static void test(int n) {
		String s = foo(n);
		System.out.println(s);
	}

	public static void main(String args[]) {
		/*
		 * int n = new Random().nextInt(); System.out.println(new Tricky().foo(n));
		 */
		test(5);
	}
}
