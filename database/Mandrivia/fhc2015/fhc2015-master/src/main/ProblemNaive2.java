package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProblemNaive2 extends Problem {

	public void resolve() {
		System.out.println("Launch problem naive 2");
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File("data/scoreList"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		for(int k=0; k<data.getnZ(); k++) {
		
			int n = 0;
			
			for(int i=0; i<data.getnX(); i++) {
				for(int j=0; j<data.getnY(); j++) {
					List<Integer> path;
					if((path = findPathTo(i, j, k)) != null) {
						int score = computePathScore(i, j, k, data.getNbTurn()-path.size());
						int score2 = getScorePath(path);
						System.out.println(score2+" + "+score);
						writer.println(i+" "+j+" "+k+" "+score+score2);
						n++;
					}
				}
			}
			
			System.out.println(k+" => "+n);
		}
		
		writer.close();
	}

	
	/**
	 * Return coverage score for the case (x, y)
	 */
	public int getScoreBalloon(int x, int y) {
		int score = 0;
		
		// If the balloon is not in the map
		if(y >= this.data.getnY() || y < 0)
			return -1;
		
		// (x - u)^2 + (columndist(y, v))^2 < V^2 => score + 1
		for(int i = (x - this.data.getCoverageRadius()) % this.data.getnX(); 
				i <= (x + this.data.getCoverageRadius()) % this.data.getnX(); i++) {
			for(int j = y - (this.data.getCoverageRadius() - columnDist(x, i));
					j <= y + (this.data.getCoverageRadius() - columnDist(x, i)); j++) {
				if(this.data.isTarget(i,j) && this.data.isCovered(this.data.getTargetIndex(i, j)) == 0)
					score++;
			}
		}
		return score;
	}

}
