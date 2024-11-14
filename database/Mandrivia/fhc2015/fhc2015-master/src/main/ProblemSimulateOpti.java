package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ProblemSimulateOpti extends Problem {
	
	private String inPathname;
	
	private int[][] targetCell;
	private Coord2[][][] destinationCell;

	public void resolve() {
		
		//read input
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inPathname));
			
			String line;
			
			int currentTurn = 0;
			while((line = reader.readLine()) != null) {
				Scanner sc = new Scanner(line);
				
				for(int i=0; i<data.getNbBalloon(); i++) {
					this.move[currentTurn][i] = sc.nextInt();
				}
				
				currentTurn++;
			}
			
			reader.close();
			
			if(currentTurn != data.getNbTurn())
				throw new RuntimeException("Incorrecte number of turn : "+currentTurn);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Read resultat done !");
		
		preCompute();
		
		System.out.println("Pre-compute done !");
		int currentScore = computeScore();
		System.out.println("Original score : "+currentScore);
		/*
		Random r = new Random();

		while(true) {
			int b=r.nextInt(data.getNbBalloon());
			int t=r.nextInt(data.getNbTurn());
			int m=r.nextInt(1)+1;
			
			int oldm = this.move[t][b];
			
			this.move[t][b] = (this.move[t][b]+1+m)%3-1;
			
			int score = computeScore();
			if(score == currentScore) {
				if(r.nextBoolean()) {
					this.move[t][b] = oldm;
				}
			}
			else if(score > currentScore) {
				System.out.println("Score improvment ! "+currentScore+" => "+score);
				currentScore = score;
				try {
					output("data/outOpti");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			
		}*/
	}
	
	public void preCompute() {
		/*
		 * Target cell
		 */
		
		targetCell = new int[data.getnX()][data.getnY()];
		
		for(int i=0; i<data.getTargetsCase().size(); i++) {
			targetCell[data.getTargetsCase().get(i).x][data.getTargetsCase().get(i).y]++;
			
			if(targetCell[data.getTargetsCase().get(i).x][data.getTargetsCase().get(i).y]>1)
				throw new RuntimeException("servals cell on same case");
		}
		
		/*
		 * Destination cell
		 */
		
		destinationCell = new Coord2[data.getnX()][data.getnY()][data.getnZ()];
		
		for(int i=0; i<data.getnX(); i++) {
			for(int j=0; j<data.getnY(); j++) {
				for(int k=0; k<data.getnZ(); k++) {
					Coord2 vector = data.getWindVector(i, j, k);
					destinationCell[i][j][k] = j+vector.y >= 0 && j+vector.y < data.getnY() ? new Coord2(oRingCompute(i+vector.x), j+vector.y) : null;
				}
			}
		}
	}
	
	public int computeScore() {
		
		int score = 0;
		int scoreBalloon[] = new int[data.getNbBalloon()];
		
		Coord3[] coordinates = new Coord3[data.getNbBalloon()];
		for(int b=0; b<data.getNbBalloon(); b++) {
			coordinates[b] = new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, -1);
		}
		
		
		for(int t=0; t<data.getNbTurn(); t++) {
			
			boolean coverredMap[][] = new boolean[data.getnX()][data.getnY()];
			
			for(int b=0; b<data.getNbBalloon(); b++) {
				
				if(coordinates[b] == null)
					continue;

				int newZ = coordinates[b].z+move[t][b];
				
				
				if(newZ == -1)
					continue;
				
				Coord2 newCoord = null;
				if(newZ >= 0 && newZ < data.getnZ())
					newCoord =destinationCell[coordinates[b].x][coordinates[b].y][newZ];
				
				if(newCoord == null) {
					//System.out.println("Turn "+(t+1)+" balloon "+(b+1)+" out ");
					continue;
				}
				

				
				coordinates[b] = new Coord3(newCoord.x, newCoord.y, newZ);

				int currentScore = 0;
				for(int x = -data.getCoverageRadius()-1; x < data.getCoverageRadius()+1; x++) {
					int cX = oRingCompute(coordinates[b].x+x);
					for(int y = -data.getCoverageRadius()-1; y < data.getCoverageRadius()+1; y++) {
						int cY = coordinates[b].y+y;
						if(cY >= 0 && cY < data.getnY()) {
							if(x*x+y*y <= data.getCoverageRadius()*data.getCoverageRadius()) {
								if(!coverredMap[cX][cY] && targetCell[cX][cY] == 1) {
									currentScore++;
									scoreBalloon[b]++;
								}
								coverredMap[cX][cY] = true;
							}
						}
					}

				}
				
	
				score += currentScore;

			}

		}
		
		System.out.println("->"+scoreBalloon[0]);
		return score;
	}

}
