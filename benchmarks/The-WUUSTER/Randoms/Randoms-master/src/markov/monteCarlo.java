package markov;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/** filtered by PAClab */
 public class monteCarlo {
	private int[] alphabet;
	private double[][] matrix;
	private int[][] mapping;
	private double plaus;
	
	private int indexLookup(int ch) {
		for(int i = 0; i < alphabet.length; i++) {
			if(alphabet[i]==ch) {
				return i;
			}
		}
		return -1;
	}
	public String toString() {
		String result = "Rows are first character, Columns are second character\n";
		for(int i = 0; i < matrix.length; i++) {
			if(i==0) {
				result += "  ";
				for(; i < alphabet.length; i++) {
					result += "          " + (char)alphabet[i] + "          ";
				}
				result += "\n";
				i=0;
			}
			for(int j = 0; j < matrix.length; j++) {
				if(j==0) result += (char)alphabet[i] + " ";
				result += matrix[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
}