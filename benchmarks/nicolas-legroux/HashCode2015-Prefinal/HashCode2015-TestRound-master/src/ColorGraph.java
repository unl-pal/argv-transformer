import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

public class ColorGraph {

	HashMap<Part, Integer> neighbors;
	Problem pb;

	private Solution bestSolution = null;

	public void solveWithRandomRemovals(double prob, int max_iterations) {

		// Start initial solver
		if (bestSolution == null) {
			bestSolution = new Solution(pb);
			bestSolution = solve(bestSolution, 3);
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date) + " "
				+ bestSolution.getScore());

		int bestEverSolution = bestSolution.getScore();
		int countNoProgression = 0;

		double probChange = prob;

		int resetCounter = 0;

		int i = 1;

		Random random = new Random(0);

		// Iterate
		while (true) {

			// Copy the best solution
			Solution baseSolution = new Solution(bestSolution);
			HashSet<Part> parts = new HashSet<Part>();

			for (Part p : baseSolution.getParts()) {
				if (random.nextDouble() <= probChange) {
					parts.add(p);
				}
			}

			baseSolution.resetParts(parts);

			i++;

			if (bestSolution.getScore() > 9600) {
				baseSolution = solve(baseSolution, 8);
				// System.out.println("Solving with more than 3 Hams / piece");
			}

			else {
				baseSolution = solve(baseSolution, 3);
			}

			if (baseSolution.getScore() >= bestSolution.getScore()) {
				// System.out.println("Score : " + bestSolution.getScore() +
				// " -> " + baseSolution.getScore());
				if (baseSolution.getScore() > bestSolution.getScore()) {
					countNoProgression = 0;
					date = new Date();
					System.out.println(dateFormat.format(date) + " "
							+ baseSolution.getScore());
					try {
						baseSolution.save("results" + resetCounter + ".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					countNoProgression++;
					// System.out.println("No progression for the " +
					// countNoProgression + "th time");
				}

				bestSolution = baseSolution;
				probChange = prob;
			} else {
				probChange = Math.min((probChange + 0.01), 1.0);
			}
		}
	}
}