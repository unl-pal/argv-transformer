/*
 * Headers above the package
 * header2
 */
// header using single line
// another header using single line

package transformer.integration;
/*
 * Headers below the package
 * header2
 */
// header with single line
// another header
import util.ArgVRandom;

/**
 * This class is a copy of SymbolicIntTest with comments added in.
 * This is a multiline JavaDoc Comment on the class
 * @author Charles Moloney
 */
public class PreservationOfCommentsTest {
	/**
	 * This is a JavaDoc Comment on a method
	 * @param input1 an integer
	 * @param input2 another integer
	 * @return something nonsensical from our if statements
	 */
	public static int splitter(int input1, int input2) {
		int value = (int) (ArgVRandom.randomInt() * 10);
		/* This is a single line block comment */
		// This is a single line comment
		/*
		 * This is a multi-line block comment
		 * Second line
		 */
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static void main (String[] args) {
		// int fake = Fake.getInt();
		int time = (int) System.nanoTime();
		int var2 = (int) (Math.random() * 5);
		System.out.println(splitter(time, var2));
	}
}

// end of file comments
/*
 * More end of file comments
 * more end of file commments
 */
