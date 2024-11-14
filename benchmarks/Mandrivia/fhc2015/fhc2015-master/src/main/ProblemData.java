/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;

public class ProblemData {
	
	/** PACLab: suitable */
	 public int getScoreBalloon(int x, int y) {
		int coverageRadius = Verifier.nondetInt();
		int score = 0;
		
		// If the balloon is not in the map
		if(x >= Verifier.nondetInt() || y < 0)
			return -1;
		
		// (x - u)^2 + (columndist(y, v))^2 < V^2 => score + 1
		for(int i = x - coverageRadius ; i <= x + coverageRadius ; i++) {
			for(int j = y - Verifier.nondetInt();
					j <= y + Verifier.nondetInt(); j++) {
				if(Verifier.nondetBoolean())
					score++;
			}
		}
		return score;
	}
	

}
