package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProblemNaive3 extends Problem {
	
	public void resolve() {
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(new File("data/scoreRareList"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//load file
		BufferedReader reader = null;;
		final int scoreList[][][] = new int[this.data.getnX()][this.data.getnY()][this.data.getnZ()];
		try {
			reader = new BufferedReader(new FileReader("data/scoreList"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line;
		try {
			while((line = reader.readLine()) != null) {
				Scanner sc = new Scanner(line);
				
				int x = sc.nextInt();
				int y = sc.nextInt();
				int z = sc.nextInt();
				int score = sc.nextInt();
				
				scoreList[x][y][z] = score;
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Coord3> sortedIndex = new ArrayList<Coord3>();
		int targetScore[] = new int[data.getTargetsCase().size()];

		
		for(int i=0; i<data.getnX(); i++) {
			for(int j=0; j<data.getnY(); j++) {
				for(int k=0; k<data.getnZ(); k++) {
					if (scoreList[i][j][k] != 0) {
						System.out.println(i+" "+j+" "+k);
						List<Integer> path = findPathTo(i, j, k);
						for (int l = 0 ; l < data.getTargetsCase().size() ; l++) {
							if (passBy(data.getTargetsCase().get(l), path)) {
								targetScore[l]++;
								
							}
						}
					}
					if(scoreList[i][j][k] != 0)
						sortedIndex.add(new Coord3(i, j, k));
				}
			}	
		}
		
	/*	for (int i = 0 ; i < targetScore.length ; i++) {
			System.out.println(targetScore[i]);
		}
		*/

		int [][][] scoreRareByPath = new int [data.getnX()][data.getnY()][data.getnZ()];
		
		for(int i=0; i<data.getnX(); i++) {
			for(int j=0; j<data.getnY(); j++) {
				for(int k=0; k<data.getnZ(); k++) {
					if (scoreList[i][j][k] != 0) {
						List<Integer> path = findPathTo(i, j, k);
						scoreRareByPath[i][j][k] = this.getScoreRare(path, targetScore);
						System.out.println(i+" "+j+" "+k+" "+scoreRareByPath[i][j][k]);
						writer.println(i+" "+j+" "+k+" "+scoreRareByPath[i][j][k]);
					}
				}
			}
			
		}
		
		writer.close();
		
	
		
		/*Collections.sort(sortedIndex, new Comparator<Coord3>() {

			public int compare(Coord3 index1, Coord3 index2) {
				return scoreList[index2.x][index2.y][index2.z]-scoreList[index1.x][index1.y][index1.z];
			}
		});
		
		List<List<Integer>> listPath = new ArrayList<List<Integer>>();
		
		int decalage = 0;
		
		for(int i=0; i<data.getNbBalloon(); i++) {
			
			if(i > 0) 
				while(commonPercentage(findPathTo(sortedIndex.get(i + decalage).x, sortedIndex.get(i + decalage).y, sortedIndex.get(i + decalage).z), 
						listPath.get(listPath.size()-1)) > 72)
					decalage = (decalage + 1) % sortedIndex.size();
					
			Coord3 c = sortedIndex.get(i + decalage);
			listPath.add(findPathTo(c.x, c.y, c.z));
			System.out.println(c.x+" "+c.y+" "+c.z);
		}
		
		int tempo = 1;
		
		for(int t=0; t<data.getNbTurn(); t++) {
			for(int i=0; i<data.getNbBalloon(); i++) {
				if(t >= listPath.get(i).size() || t-tempo*i < 0)
					this.move[t][i] = 0;
				else
					this.move[t][i] = listPath.get(i).get(t-tempo*i);
				
			}
		}*/


		
	}

}
