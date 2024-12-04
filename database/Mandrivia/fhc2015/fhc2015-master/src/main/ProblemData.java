package main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProblemData {
	
	private int nX;
	private int nY;
	private int nZ;
	
	private int nbTarget;
	private int coverageRadius;
	private int nbBalloon;
	private int nbTurn;
	
	private List<Coord2> targetsCase;
	
	private Coord2[][][] windsVectors;
	
	private Coord3[] balloonsCoord;
	// [] = tour, [][] = balloon
	private int[][] altitudeChanges;
	
	private Coord2 startBalloon;
	
	private Map<Integer, Integer> targetCovered;
	
	public int getScoreBalloon(int x, int y) {
		int score = 0;
		
		// If the balloon is not in the map
		if(x >= getnY() || y < 0)
			return -1;
		
		// (x - u)^2 + (columndist(y, v))^2 < V^2 => score + 1
		for(int i = x - this.coverageRadius ; i <= x + this.coverageRadius ; i++) {
			for(int j = y - (getCoverageRadius() - columnDist(x, i));
					j <= y + (getCoverageRadius() - columnDist(x, i)); j++) {
				if(isTarget(i,j))
					score++;
			}
		}
		return score;
	}
	

}
