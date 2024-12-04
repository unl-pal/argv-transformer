package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProblemSimulatePass2 extends ProblemSimulate {
	
	private String previousOut;

	public void resolvePass() {
		loadOutput(previousOut);
		
		
		
		int bestScore = scoreChecking();
		System.out.println("actually : "+bestScore);
		while(true) {
			
			for(int currentBalloon=0; currentBalloon<data.getNbBalloon(); currentBalloon++) {
	
				System.out.println("Launch simulation for balloon "+(currentBalloon+1)+"/"+data.getNbBalloon());
	
				resetWithoutBalloon(currentBalloon);
				
				computeCellScore();
				resetDynamicCopmpute();
				
				
				int score = computeBallonPath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0);

				System.out.println("find score : "+score);
	
				List<Integer> path = new ArrayList<Integer>();
				backtracePath(new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0), 0, path);
				System.out.println("path length : "+path.size());
	
				for(int i=0; i<data.getNbTurn(); i++) {
					this.move[i][currentBalloon] = i < path.size() ? path.get(i) : 0;
				}
				
				int scores = scoreChecking();
				System.out.println("Total : "+scores);
				
				if(scores > bestScore) {
					try {
						output("data/pass2_2");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					bestScore = scores;
				}
			}
		}
		
	}

	private void resetWithoutBalloon(int currentBalloon) {
		resetCover();
		
		Coord3[] coordinates = new Coord3[data.getNbBalloon()];
		for(int b=0; b<data.getNbBalloon(); b++) {
			coordinates[b] = new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0);
		}
		
		
		for(int t=0; t<data.getNbTurn(); t++) {
			
			
			for(int b=0; b<data.getNbBalloon(); b++) {
				
				if(b == currentBalloon)
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
