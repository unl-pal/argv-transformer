package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProblemSimulatePass1 extends ProblemSimulate {

	public void resolvePass() {
		int totale = 0;
		for(int b=0; b<data.getNbBalloon(); b++) {
			computeCellScore();
			
			resetDynamicCopmpute();
			
			int score = computeBallonPath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0);
			totale += score;
			System.out.println(b+" = "+score+" (totale = "+totale+")");
			
			List<Integer> path = new ArrayList<Integer>();
			backtracePath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0, path);
			
			System.out.println("length : "+path.size());
			
			for(int i=0; i<data.getNbTurn(); i++) {
				move[i][b] = i < path.size() ? path.get(i) : 0;
			}
			
			int checking = scoreChecking();
			
			if(checking != totale) {
				System.out.println("Checking failed ! "+totale+" / "+checking);
			}
		}
	}

}
