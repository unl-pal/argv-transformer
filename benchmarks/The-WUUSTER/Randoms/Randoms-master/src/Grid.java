
import java.util.Scanner;

/** filtered by PAClab */
 public class Grid {
	public static void update2(int[][] board, int width, int height) {
		int x, y;
		int[][] neighbors = new int[height][width];
		for (x = 0; x < height; x++) {
			for (y = 0; y < width; y++) {
				int minRow = Math.max(0, x - 1);
			    int maxRow = Math.min(height - 1, x + 1);
			    int minCol = Math.max(0, y - 1);
			    int maxCol = Math.min(width - 1, y + 1);

			    for (int row = minRow; row <= maxRow; row++) {
			    	for (int col = minCol; col <= maxCol; col++) {
			    		if (row != x || col != y) {
			    			if(board[row][col] == 1){
			    				neighbors[x][y]++;
			    			}
			    		}
			    	}
			    }
			}
		}
		for (x = 0; x < height; x++) {
			for (y = 0; y < width; y++) {
				if (board[x][y] == 1)
				{
					if (neighbors[x][y] < 2 || neighbors[x][y] > 3)
						board[x][y] = 0;
				}
				else
				{
					if (neighbors[x][y] == 3)
						board[x][y] = 1;
				}
			}
		}
	}
	
	//Updates the board given appropriate rules
	public static void update(int[][] board, int width, int height) {
		int i, j;
		int[][]neighbor_grid = new int[height][width];
		//for any board with at least one dimension of size 1
		if (width == 1 || height == 1)
		{
			if (height > 1)
			{
				for (int x = 1; x < height - 1; x++)
				{
					if (board[x-1][0] == 1)
						neighbor_grid[x][0]++;
					if (board[x+1][0] == 1)
						neighbor_grid[x][0]++;
				}
			}
			if (width > 1)
			{
				for (int y = 1; y < width - 1; y++)
				{
					if (board[0][y-1] == 1)
						neighbor_grid[0][y]++;
					if (board[0][y+1] == 1)
						neighbor_grid[0][y]++;
				}
			}
		}
		else {
			//checks the neighbors and counts how many neighbors per cell
			for (i = 0; i < height; i++) {
				for (j = 0; j < width; j++) {
					if ((i > 0 && j > 0) && (i < height - 1 && j < width - 1)) {
						if (board[i-1][j-1] == 1)
							neighbor_grid[i][j]++;
						if (board[i-1][j] == 1)
							neighbor_grid[i][j]++;
						if (board[i-1][j+1] == 1)
							neighbor_grid[i][j]++;
						if (board[i][j-1] == 1)
							neighbor_grid[i][j]++;
						if (board[i][j+1] == 1)
							neighbor_grid[i][j]++;
						if (board[i+1][j-1] == 1)
							neighbor_grid[i][j]++;
						if (board[i+1][j] == 1)
							neighbor_grid[i][j]++;
						if (board[i+1][j+1] == 1)
							neighbor_grid[i][j]++;
					}
					
					//accounting for boundary cases
					else {
						//if it's in the first row
						if (i == 0) {
	        	            if (board[i+1][j] == 1)
	        	            	neighbor_grid[i][j]++;
							if (j == 0) {
								if (board[i][j+1] == 1)
									neighbor_grid[i][j]++;
								if (board[i+1][j+1] == 1)
									neighbor_grid[i][j]++;
							}
							if (j > 0) {
								if (board[i][j-1] == 1)
									neighbor_grid[i][j]++;
								if (board[i+1][j-1] == 1)
									neighbor_grid[i][j]++;
								if (j < width - 1) {
									if (board[i][j+1] == 1)
										neighbor_grid[i][j]++;
									if (board[i+1][j+1] == 1)
										neighbor_grid[i][j]++;
								}
							}
						}
						//last row
						else if (i == height - 1) {
							if (board[i-1][j] == 1)
								neighbor_grid[i][j]++;
							if (j == 0) {
								if (board[i][j+1] == 1)
									neighbor_grid[i][j]++;
								if (board[i-1][j+1] == 1)
									neighbor_grid[i][j]++;
							}
							if (j > 0) {
								if (board[i][j-1] == 1)
									neighbor_grid[i][j]++;
								if (board[i-1][j-1] == 1)
									neighbor_grid[i][j]++;
								if (j < width - 1) {
									if (board[i][j+1] == 1)
										neighbor_grid[i][j]++;
									if (board[i-1][j+1] == 1)
										neighbor_grid[i][j]++;
								}
							}
						}
						else {
							if (board[i-1][j] == 1)
								neighbor_grid[i][j]++;
							if (board[i+1][j] == 1)
								neighbor_grid[i][j]++;
							//first column excluding first/last rows
							if (j == 0) {
								if (board[i][j+1] == 1)
									neighbor_grid[i][j]++;
								if (board[i+1][j+1] == 1)
									neighbor_grid[i][j]++;
								if (board[i-1][j+1] == 1)
									neighbor_grid[i][j]++;
							}
							//last column excluding first/last rows
							if (j == width - 1) {
								if (board[i][j-1] == 1)
									neighbor_grid[i][j]++;
								if (board[i+1][j-1] == 1)
									neighbor_grid[i][j]++;
								if (board[i-1][j-1] == 1)
									neighbor_grid[i][j]++;
							}
						}
					}
				}
			}
			
		}
		//updates the grid based on neighbor grid
		for (int x = 0; x < height; x++)
		{
			for (int y = 0; y < width; y++)
			{
				if (board[x][y] == 1)
				{
					if (neighbor_grid[x][y] < 2 || neighbor_grid[x][y] > 3)
						board[x][y] = 0;
				}
				else
				{
					if (neighbor_grid[x][y] == 3)
						board[x][y] = 1;
				}
			}
		}
	}
}

