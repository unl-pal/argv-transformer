package tests.IntOpCounterTests;
/**
 * 8
 *
 */
public class TestChar {

	public static void main(String[] args) {
		char c = 'D';
		int x = c++; // 1
		System.out.println(c + " " + x);
		System.out.println(c + x); //1
		
		double d = 'a' + 'b' / 5; // 2
		int b = -'a'; // 0
		char e = 'a' + 'b' + 'c'; // 2
		
		System.out.println((int) ('a' - 9.6 / (double) 5)); // 2
		
	}
}
