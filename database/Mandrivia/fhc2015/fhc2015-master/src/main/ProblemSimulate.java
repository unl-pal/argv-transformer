package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public abstract class ProblemSimulate extends Problem {
	
	protected boolean[][][] coverredMap;
	private int[][][][] dynamicScore;
	private int[][][][] dynamicPath;
	protected int[][][] currentScore;
	protected boolean[][] targetCell;
	protected Coord3[][][] destinationCell;
	private Random tieBreak;

	private void preComputeAtStart() {
		coverredMap = new boolean[data.getnX()][data.getnY()][data.getNbTurn()+1];
		
		 resetCover();
		
		dynamicScore = new int[data.getnX()][data.getnY()][data.getnZ()+1][data.getNbTurn()+1];
		dynamicPath = new int[data.getnX()][data.getnY()][data.getnZ()+1][data.getNbTurn()+1];
		
		currentScore = new int[data.getnX()][data.getnY()][data.getNbTurn()+1];
		
		targetCell = new boolean[data.getnX()][data.getnY()];
		
		for(int x=0; x<data.getnX(); x++) {
			Arrays.fill(targetCell[x], false);
		}
		
		for(int i=0; i<data.getTargetsCase().size(); i++)
			targetCell[data.getTargetsCase().get(i).x][data.getTargetsCase().get(i).y] = true;
		
		destinationCell = new Coord3[data.getnX()][data.getnY()][data.getnZ()+1];
		for(int x=0; x<data.getnX(); x++) {
			for(int y=0; y<data.getnY(); y++) {
				for(int z=0; z<data.getnZ()+1; z++) {
					if(z == 0)
						destinationCell[x][y][z] = null;
					else {
						Coord3 destination = null;
						
						Coord2 vector = data.getWindVector(x, y, z-1);
						
						int destX = x+vector.x;
						
						if(destX >= data.getnX()) {
							destX = destX  - data.getnX();
						}
						else if(destX < 0) {
							destX = data.getnX() + destX;
						}

						int destY = y+vector.y;
						
						if(destY >= 0 && destY < data.getnY())
							destination = new Coord3(destX, destY, z);
						
						destinationCell[x][y][z] = destination;
					}
				}
			}
		}
	}
	
	public void computeCellScore() {
		int radiusSquare = data.getCoverageRadius()*data.getCoverageRadius();
		for(int x=0; x<data.getnX(); x++) {
			for(int y=0; y<data.getnY(); y++) {
				for(int t=0; t<data.getNbTurn()+1; t++) {
					int score = 0;
					for(int diffX = -data.getCoverageRadius()-1; diffX < data.getCoverageRadius()+1; diffX++) {
						int destX = x+diffX;
						
						if(destX >= data.getnX()) {
							destX = destX  - data.getnX();
						}
						else if(destX < 0) {
							destX = data.getnX() + destX;
						}
						for(int diffY = -data.getCoverageRadius()-1; diffY < data.getCoverageRadius()+1; diffY++) {
							int destY = y+diffY;
							
							if(destY >= 0 && destY < data.getnY()) {
								if(diffX*diffX+diffY*diffY <= radiusSquare) {
									if(!coverredMap[destX][destY][t] && targetCell[destX][destY])
										score++;
								}
							}
						}
					}
					
					currentScore[x][y][t] = score;
					
				}
			}
		}
	}
	
	public int scoreChecking() {
		int score = 0;
		int scoreBalloon[] = new int[data.getNbBalloon()];
		
		Coord3[] coordinates = new Coord3[data.getNbBalloon()];
		for(int b=0; b<data.getNbBalloon(); b++) {
			coordinates[b] = new Coord3(data.getStartBalloon().x, data.getStartBalloon().y, 0);
		}
		
		
		for(int t=0; t<data.getNbTurn(); t++) {
			
			boolean coverredMap[][] = new boolean[data.getnX()][data.getnY()];
			
			for(int b=0; b<data.getNbBalloon(); b++) {
				
				if(coordinates[b] == null)
					continue;
				
				if(coordinates[b].z == 1 && move[t][b] == -1) {
					System.out.println("Error go too low.");
					System.exit(0);
				}
				else if(coordinates[b].z == 8 && move[t][b] == 1) {
					System.out.println("Error go too high.");
					System.exit(0);
				}

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
				
				

				int currentScore = 0;
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
								if(!coverredMap[cX][cY] && targetCell[cX][cY]) {
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
		
		return score;
	}
} 