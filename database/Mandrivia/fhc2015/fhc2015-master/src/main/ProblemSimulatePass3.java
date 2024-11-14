package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProblemSimulatePass3 extends ProblemSimulate {
	private String previousOut;

	public void resolvePass() {
		loadOutput(previousOut);
		
		
		
		int bestScore = scoreChecking();
		System.out.println("actually : "+bestScore);
		
		int moveSave[][] = new int[data.getNbTurn()][data.getNbBalloon()];
		
		Random r = new Random(23);
		
		while(true) {
			
			int b1 = r.nextInt(data.getNbBalloon());
			int b2 = r.nextInt(data.getNbBalloon());			
					
			if(b1 == b2)
				continue;

			System.out.println("Launch simulation for balloons "+(b1+1)+";"+(b2+1));

			copySolution(moveSave, move);

			resetWithoutBalloons(b1, b2);

			computeCellScore();
			resetDynamicCopmpute();
			computeBallonPath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0);
			List<Integer> path1 = new ArrayList<Integer>();
			backtracePath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0, path1);

			computeCellScore();
			resetDynamicCopmpute();
			computeBallonPath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0);
			List<Integer> path2 = new ArrayList<Integer>();
			backtracePath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0, path2);

			for(int i=0; i<data.getNbTurn(); i++) {
				this.move[i][b1] = i < path1.size() ? path1.get(i) : 0;
				this.move[i][b2] = i < path2.size() ? path2.get(i) : 0;
			}

			int scores = scoreChecking();
			System.out.println("Total : "+scores);

			while(true) {
				resetWithoutBalloons(b1, -1);

				computeCellScore();
				resetDynamicCopmpute();
				computeBallonPath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0);
				List<Integer> path = new ArrayList<Integer>();
				backtracePath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0, path);

				for(int i=0; i<data.getNbTurn(); i++) {
					this.move[i][b1] = i < path.size() ? path.get(i) : 0;
				}

				resetWithoutBalloons(b2, -1);

				computeCellScore();
				resetDynamicCopmpute();
				computeBallonPath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0);
				path = new ArrayList<Integer>();
				backtracePath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0, path);

				for(int i=0; i<data.getNbTurn(); i++) {
					this.move[i][b2] = i < path.size() ? path.get(i) : 0;
				}

				int nScore = scoreChecking();

				if(nScore > scores) {
					scores = nScore;
					System.out.println("Total : "+scores);
				}
				else {
					break;
				}
			}


			if(scores > bestScore) {
				try {
					output("data/pass3");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				bestScore = scores;
			}
			else {
				copySolution(move, moveSave);
			}
		}

		
	}

	private void resetWithoutBalloons(int b1, int b2) {
		resetCover();
		
		Coord3[] coordinates = new Coord3[data.getNbBalloon()];
		for(int b=0; b<data.getNbBalloon(); b++) {
			coordinates[b] = new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0);
		}
		
		
		for(int t=0; t<data.getNbTurn(); t++) {
			
			
			for(int b=0; b<data.getNbBalloon(); b++) {
				
				if(b == b1 || b == b2)
					continue;
				
				if(coordinates[b] == null)
					continue;

				int newZ = coordinates[b].z+move[t][b];
				
				if(newZ == 0)
					continue;
				
				Coord3 newCoord = null;
				if(newZ > 0 && newZ <= data.getnZ())
					newCoord = destinationCell[coordinates[b].x][coordinates[b].y][newZ];
				
				if(newCoord == null) {
					continue;
				}
				
				coordinates[b] = newCoord;
				

				for(int x = -data.getCoverageRadius()-1; x < data.getCoverageRadius()+1; x++) {
					int cX = coordinates[b].x+x;
					if(cX >= data.getnX()) {
						cX = cX  - data.getnX();
					}
					else if(cX < 0) {
						cX = data.getnX() + cX;
					}
					for(int y = -data.getCoverageRadius()-1; y < data.getCoverageRadius()+1; y++) {
						int cY = coordinates[b].y+y;
						if(cY >= 0 && cY < data.getnY()) {
							if(x*x+y*y <= data.getCoverageRadius()*data.getCoverageRadius()) {
								coverredMap[cX][cY][t] = true;
							}
						}
					}

				}
				
			}
			

		}
		
	}

}
