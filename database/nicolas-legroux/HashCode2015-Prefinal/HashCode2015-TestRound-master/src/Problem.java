import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Problem {
	
	enum PizzaTopping {
		HAM, TOMATOES
	}
	
	int width;
	int height;
	int min_ham;
	int surf_max;
	PizzaTopping[][] pizza;
	
	//For stats
	int numberOfHam = 0;
}