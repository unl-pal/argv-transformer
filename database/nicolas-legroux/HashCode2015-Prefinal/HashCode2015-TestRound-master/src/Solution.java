import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class Solution {

	private HashSet<Part> parts;
	private boolean free[][];

	Problem pb;

	void print() {
		for (int y = 0; y < pb.height; ++y) {
			for (int x = 0; x < pb.width; ++x) {
				if (!free[x][y] && pb.pizza[x][y] == Problem.PizzaTopping.HAM)
					System.out.print("X");
				else if (!free[x][y])
					System.out.print("x");
				else if (pb.pizza[x][y] == Problem.PizzaTopping.HAM)
					System.out.print(".");
				else
					System.out.print(" ");
			}
			System.out.print("\n");
		}

		System.out.println("\nThe score of the solution is " + getScore());
	}
}