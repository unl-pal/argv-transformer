package tests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import constant.CT;

public class MatrixBoard implements Serializable {

	private byte[][] board;

	private int countGames;
	private int countWins;
	private int countLoses;

	// private static int amountWhites;
	// private static int amountBlacks;
	private static int countFreeTurns;

	private static boolean afterHit = false;
	private boolean white;
	private static boolean staticWhite;
	private static int terminal = 0;
	private static Stack<MatrixBoard> pastTurns;
	private int lastAtackI = -1;
	private int lastAtackJ = -1;
	// private boolean kings;
	private static int countTurns;
	private int countMoves;
	private Random rand = new Random();
	private ArrayList<MatrixBoard> nextTurns;

	private boolean myHit;
	private boolean enemyHit;
	private static MatrixBoard root;

	public MatrixBoard(byte[][] inBoard) {
		board = inBoard;
		white = staticWhite;
		countMoves = countTurns;
		// startBoardEight();
	}

	// public MatrixBoard(int lastI,int lastJ,byte[][] inBoard) {
	// this(inBoard);
	// if(afterHit){
	// lastAtackI = lastI;
	// lastAtackJ = lastJ;
	// }
	//
	// // startBoardEight();
	// }

	public MatrixBoard(byte[][] inBoard, int lastI, int lastJ) {
		this(inBoard);
		if (afterHit) {
			lastAtackI = lastI;
			lastAtackJ = lastJ;
		}

		// startBoardEight();
	}

	public MatrixBoard() {
		int N = CT.SIZE_BOARD;

		board = new byte[N][N];

		N /= 2;
		// amountWhites = N * (N - 1);
		// amountBlacks = amountWhites;

		pastTurns = new Stack<>();

		startBoardEight();
		// startBoardTest();

		white = false;
		// pastTurns.push(this);
		// root=this;
	}

	public void startBoardTest() {

		boolean black = true;
		if (black) {
			 board[1][2] = -1;
			 board[1][4] = -1;
			 board[3][4] = -1;
			 board[5][4] = -1;
			 board[2][1] = 1;
			
			// board[3][2] = -1;
			// board[5][2] = -1;
		}
		else {
			board[1][2] = 1;
			board[1][4] = 1;
			board[3][4] = 1;
			board[5][4] = 1;
			board[2][1] = -2;

			 board[3][2] = 1;
			board[5][2] = 1;
		}

	

		// board[1][2] = 1;
		// board[3][4] = 1;
		// board[5][4] = 1;
		// board[0][1] = -1;
		//
		// board[3][2] = 1;
		// board[5][2] = 1;

		board[0][7] = 2;
		board[7][0] = -2;
		// for (int i = 0; i < board.length; i++) {
		// for (int j = 0; j < board.length; j++) {
		// if (i < CT.SIZE_BOARD / 2 - 1 && (i + j) % 2 != 0)
		//
		// if (i > CT.SIZE_BOARD / 2 && (i + j) % 2 != 0)
		// board[i][j] = 1;
		// }
		// }
	}

	public void initBoard(MatrixBoard in) {
		root = in;
		pastTurns.push(in);
		countGames = in.countGames;
		countWins = in.countWins;
		countLoses = in.countLoses;
		nextTurns = in.nextTurns;
	}

	public MatrixBoard getRoot() {
		terminal = 0;
		countFreeTurns = 0;
		pastTurns = new Stack<>();
		pastTurns.push(root);
		return root;
	}

	public void callMakeNextTurns() {
		if (nextTurns == null)
			makeNextTurns();
	}

	public byte[][] getNextMoveTable(int n) {
		callMakeNextTurns();
		countTurns++;
		MatrixBoard turn = nextTurns.get(n);
		pastTurns.push(turn);
		countFreeTurns++;
		return nextTurns.get(n).BOARD();

	}

	public MatrixBoard getUserMove(int n) {
		callMakeNextTurns();
		countTurns++;
		MatrixBoard turn = nextTurns.get(n);
		pastTurns.push(turn);
		countFreeTurns++;
		return nextTurns.get(n);

	}

	public MatrixBoard getNextTurn() {
		callMakeNextTurns();
		MatrixBoard turn = null;
		if (!myHit||CT.LEARNING) {
			countTurns++;
			turn = nextTurns.get(getBestMove());
			pastTurns.push(turn);
			countFreeTurns++;

		}

		return turn;

	}

	public int endGame() {
		callMakeNextTurns();

		if (countFreeTurns == CT.FREE_MOVES)
			terminal = -1; // draw
		if (nextTurns.size() == 0)
			terminal = -2;

		if (terminal < 0) {
			// System.out.println(white);
			if (terminal == -1) {
				while (!pastTurns.empty()) {
					MatrixBoard temp = pastTurns.pop();
					temp.countGames++;

					if (temp.countMoves == 0)
						root = temp;
					// System.out.println(temp);
				}
			}
			if (terminal == -2) {
				while (!pastTurns.empty()) {
					MatrixBoard temp = pastTurns.pop();
					if (white) {
						if (temp.white)
							temp.countLoses++;
						else
							temp.countWins++;

					} else {
						if (!temp.white)
							temp.countLoses++;
						else
							temp.countWins++;
					}
					temp.countGames++;
					if (temp.countMoves == 0) {
						root = temp;
						
						// System.out.println(temp);
					}
					//System.out.println(temp.white);
				}
			}
			// for (MatrixBoard mb : pastTurns) {
			// System.out.println("---------------------");
			// System.out.println(mb);
			// System.out.println("---------------------");
			// }
		}
		return terminal;
	}

	public void startGame() {
		startBoardEight();
	}

	private byte[] toOneArray(byte[][] inBoard) {
		int N = CT.SIZE_BOARD;
		byte[] res = new byte[N * N];
		for (int i = 0; i < inBoard.length; i++) {
			for (int j = 0; j < inBoard.length; j++) {
				res[i * N + j] = inBoard[i][j];
			}
		}
		return res;
	}

	private byte[][] toTwoAray(byte[] inRow) {
		int N = CT.SIZE_BOARD;
		byte[][] res = new byte[N][N];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res.length; j++) {
				res[i][j] = inRow[i * N + j];
			}
		}
		return res;
	}

	public void writeToFile(MatrixBoard mb, DataOutputStream out)
			throws IOException {
		int games = mb.countGames;
		out.writeInt(games);
		int wins = mb.countWins;
		out.writeInt(wins);
		int loss = mb.countLoses;
		out.writeInt(loss);
		byte[][] board = mb.board;
		out.write(toOneArray(board));
		int amountChildren = 0;

		if (mb.nextTurns != null) {
			amountChildren = mb.nextTurns.size();
			// System.out.print("*"+amountChildrens+ "*");
			out.writeInt(amountChildren);
			for (MatrixBoard nt : mb.nextTurns) {
				writeToFile(nt, out);
			}

		} else {
			// System.out.print("|"+amountChildrens+"|");
			out.writeInt(amountChildren);
		}

	}

	public MatrixBoard readFromeFile(MatrixBoard mb, DataInputStream in)
			throws IOException {
		int N = CT.SIZE_BOARD;
		int g = in.readInt();
		mb.countGames = g;
		int w = in.readInt();
		mb.countWins = w;
		int l = in.readInt();
		mb.countLoses = l;
		byte[] row = new byte[N * N];
		in.read(row);
		mb.board = toTwoAray(row);
		int children = in.readInt();
		// System.out.println(childrens);
		if (children != 0) {
			mb.nextTurns = new ArrayList<>();
			for (int i = 0; i < children; i++) {
				mb.nextTurns.add(readFromeFile(new MatrixBoard(), in));
			}
		}

		else
			mb.nextTurns = null;
		return mb;

	}

	private void startBoardEight() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = 0;
				if (i < CT.SIZE_BOARD / 2 - 1 && (i + j) % 2 != 0)
					board[i][j] = -1;
				if (i > CT.SIZE_BOARD / 2 && (i + j) % 2 != 0)
					board[i][j] = 1;
			}
		}
	}

	private void makeWhiteTurns() {
		staticWhite = true;

		if (!canMakeWhiteBeat()) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] > 0) {
						int ai = i - 1;
						int aj = j - 1;
						if (inBounds(ai, aj))
							if (board[ai][aj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, ai,
										aj)));
								// System.out.println(pastTurns.peek());
							}

						int bi = i - 1;
						int bj = j + 1;
						if (inBounds(bi, bj))
							if (board[bi][bj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, bi,
										bj)));
							}
						if (board[i][j] == 2) {
							int ci = i + 1;
							int cj = j - 1;
							if (inBounds(ci, cj))
								if (board[ci][cj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											ci, cj)));

								}

							int di = i + 1;
							int dj = j + 1;
							if (inBounds(di, dj))
								if (board[di][dj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											di, dj)));
								}

						}
					}
				}
			}

		}

	}

	private void makeBlackTurns() {
		staticWhite = false;
		if (!canMakeBlackBeat()) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] < 0) {
						int ci = i + 1;
						int cj = j - 1;
						if (inBounds(ci, cj))
							if (board[ci][cj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, ci,
										cj)));

							}

						int di = i + 1;
						int dj = j + 1;
						if (inBounds(di, dj))
							if (board[di][dj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, di,
										dj)));
							}
						if (board[i][j] == -2) {
							int ai = i - 1;
							int aj = j - 1;
							if (inBounds(ai, aj))
								if (board[ai][aj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											ai, aj)));

								}

							int bi = i - 1;
							int bj = j + 1;
							if (inBounds(bi, bj))
								if (board[bi][bj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											bi, bj)));
								}
						}
					}
				}
			}
		}

	}

	private boolean canMakeWhiteBeat() {
		boolean shouldBeat = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] > 0) {
					int ai = i - 1;
					int aj = j - 1;
					if (inBounds(ai, aj))
						if (board[ai][aj] < 0) {
							if (inBounds(ai - 1, aj - 1))
								if (board[ai - 1][aj - 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											ai, aj, ai - 1, aj - 1),
											lastAtackI, lastAtackJ));
								}
						}

					int bi = i - 1;
					int bj = j + 1;
					if (inBounds(bi, bj))
						if (board[bi][bj] < 0) {
							if (inBounds(bi - 1, bj + 1))
								if (board[bi - 1][bj + 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											bi, bj, bi - 1, bj + 1),
											lastAtackI, lastAtackJ));
								}
						}
					if (board[i][j] == 2) {
						int ci = i + 1;
						int cj = j - 1;
						if (inBounds(ci, cj))
							if (board[ci][cj] < 0) {
								if (inBounds(ci + 1, cj - 1))
									if (board[ci + 1][cj - 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, ci, cj, ci + 1, cj - 1),
												lastAtackI, lastAtackJ));
									}
							}

						int di = i + 1;
						int dj = j + 1;
						if (inBounds(di, dj))
							if (board[di][dj] < 0) {
								if (inBounds(di + 1, dj + 1))
									if (board[di + 1][dj + 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, di, dj, di + 1, dj + 1),
												lastAtackI, lastAtackJ));
									}
							}

					}

				}
			}
		}
		return shouldBeat;
	}

	private boolean canMakeBlackBeat() {
		boolean shouldBeat = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] < 0) {
					int ci = i + 1;
					int cj = j - 1;
					if (inBounds(ci, cj))
						if (board[ci][cj] > 0) {
							if (inBounds(ci + 1, cj - 1))
								if (board[ci + 1][cj - 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											ci, cj, ci + 1, cj - 1),
											lastAtackI, lastAtackJ));
								}
						}

					int di = i + 1;
					int dj = j + 1;
					if (inBounds(di, dj))
						if (board[di][dj] > 0) {
							if (inBounds(di + 1, dj + 1))
								if (board[di + 1][dj + 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											di, dj, di + 1, dj + 1),
											lastAtackI, lastAtackJ));
								}
						}
					if (board[i][j] == -2) {
						int ai = i - 1;
						int aj = j - 1;
						if (inBounds(ai, aj))
							if (board[ai][aj] > 0) {
								if (inBounds(ai - 1, aj - 1))
									if (board[ai - 1][aj - 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, ai, aj, ai - 1, aj - 1),
												lastAtackI, lastAtackJ));
									}
							}

						int bi = i - 1;
						int bj = j + 1;
						if (inBounds(bi, bj))
							if (board[bi][bj] > 0) {
								if (inBounds(bi - 1, bj + 1))
									if (board[bi - 1][bj + 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, bi, bj, bi - 1, bj + 1),
												lastAtackI, lastAtackJ));
									}
							}

					}
				}
			}
		}

		return shouldBeat;
	}

	private boolean canMakeWhiteBeatAfterHit() {
		boolean shouldBeat = false;
		int i = lastAtackI;
		int j = lastAtackJ;
		if (i > -1 && j > -1) {
			// System.out.println("WHITE : i:"+i+" j:"+j);
			if (board[i][j] > 0) {
				int ai = i - 1;
				int aj = j - 1;
				if (inBounds(ai, aj))
					if (board[ai][aj] < 0) {
						if (inBounds(ai - 1, aj - 1))
							if (board[ai - 1][aj - 1] == 0) {
								shouldBeat = true;
								nextTurns.add(new MatrixBoard(beatIJ(i, j, ai,
										aj, ai - 1, aj - 1), lastAtackI,
										lastAtackJ));
							}
					}

				int bi = i - 1;
				int bj = j + 1;
				if (inBounds(bi, bj))
					if (board[bi][bj] < 0) {
						if (inBounds(bi - 1, bj + 1))
							if (board[bi - 1][bj + 1] == 0) {
								shouldBeat = true;
								nextTurns.add(new MatrixBoard(beatIJ(i, j, bi,
										bj, bi - 1, bj + 1), lastAtackI,
										lastAtackJ));
							}
					}
				if (board[i][j] == 2) {
					int ci = i + 1;
					int cj = j - 1;
					if (inBounds(ci, cj))
						if (board[ci][cj] < 0) {
							if (inBounds(ci + 1, cj - 1))
								if (board[ci + 1][cj - 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											ci, cj, ci + 1, cj - 1),
											lastAtackI, lastAtackJ));
								}
						}

					int di = i + 1;
					int dj = j + 1;
					if (inBounds(di, dj))
						if (board[di][dj] < 0) {
							if (inBounds(di + 1, dj + 1))
								if (board[di + 1][dj + 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											di, dj, di + 1, dj + 1),
											lastAtackI, lastAtackJ));
								}
						}

				}

			}
		}
		myHit = shouldBeat;
		return shouldBeat;
	}

	private boolean canMakeBlackBeatAfterHit() {
		boolean shouldBeat = false;
		int i = lastAtackI;
		int j = lastAtackJ;
		if (i > -1 && j > -1) {
			// System.out.println("BLAck : i:"+i+" j:"+j);
			if (board[i][j] < 0) {
				int ci = i + 1;
				int cj = j - 1;
				if (inBounds(ci, cj))
					if (board[ci][cj] > 0) {
						if (inBounds(ci + 1, cj - 1))
							if (board[ci + 1][cj - 1] == 0) {
								shouldBeat = true;
								nextTurns.add(new MatrixBoard(beatIJ(i, j, ci,
										cj, ci + 1, cj - 1), lastAtackI,
										lastAtackJ));
							}
					}

				int di = i + 1;
				int dj = j + 1;
				if (inBounds(di, dj))
					if (board[di][dj] > 0) {
						if (inBounds(di + 1, dj + 1))
							if (board[di + 1][dj + 1] == 0) {
								shouldBeat = true;
								nextTurns.add(new MatrixBoard(beatIJ(i, j, di,
										dj, di + 1, dj + 1), lastAtackI,
										lastAtackJ));
							}
					}
				if (board[i][j] == -2) {
					int ai = i - 1;
					int aj = j - 1;
					if (inBounds(ai, aj))
						if (board[ai][aj] > 0) {
							if (inBounds(ai - 1, aj - 1))
								if (board[ai - 1][aj - 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											ai, aj, ai - 1, aj - 1),
											lastAtackI, lastAtackJ));
								}
						}

					int bi = i - 1;
					int bj = j + 1;
					if (inBounds(bi, bj))
						if (board[bi][bj] > 0) {
							if (inBounds(bi - 1, bj + 1))
								if (board[bi - 1][bj + 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											bi, bj, bi - 1, bj + 1),
											lastAtackI, lastAtackJ));
								}
						}

				}
			}
		}
		enemyHit = shouldBeat;
		return shouldBeat;
	}

	private void makeNextTurns() {
		this.white = pastTurns.peek().white;

		nextTurns = new ArrayList<>();

		if (afterHit) {
			if (white == true) {
				if (!canMakeWhiteBeatAfterHit()) {
					afterHit = false;
					staticWhite = false;
					// makeBlackTurns();
				}

			} else {
				if (!canMakeBlackBeatAfterHit()) {
					afterHit = false;
					staticWhite = true;
					// makeWhiteTurns();

				}
			}
		}
		if (!afterHit) {
			if (!white)
				makeWhiteTurns();
			else
				makeBlackTurns();
		}
	}

	
	private int getBestMove() {

		int maxIndex = getRandInt();
		if (!CT.FIRST_STAGE_LEARNING)
		{
			if (nextTurns != null) {
				double max = 0;
				
				for (int i = 0; i < nextTurns.size(); i++) {
					MatrixBoard t = nextTurns.get(i);
					double temp = t.countLoses/(double)t.countGames;
					if(max<temp)
					{
						max=temp;
						maxIndex=i;
					}
				}
			}
		}
		
		return maxIndex;
	}

	private int getRandInt() {
		int randInt = rand.nextInt(nextTurns.size());
		return randInt;
	}

	private byte[][] moveIJ(int fi, int fj, int ti, int tj) {
		byte[][] res = Utils.arrayCopy(board);
		byte temp = res[fi][fj];
		res[fi][fj] = res[ti][tj];

		// make king
		if (temp == 1 && ti == 0)
			res[ti][tj] = 2;
		else if (temp == -1 && ti == CT.SIZE_BOARD - 1)
			res[ti][tj] = -2;
		else
			res[ti][tj] = temp;

		return res;
	}

	private byte[][] beatIJ(int fi, int fj, int mi, int mj, int ti, int tj) {
		// countTurns++;
		countFreeTurns = 0;
		byte[][] res = Utils.arrayCopy(board);
		byte temp = res[fi][fj];
		res[fi][fj] = res[ti][tj];

		// make king
		if (temp == 1 && ti == 0)
			res[ti][tj] = 2;
		else if (temp == -1 && ti == CT.SIZE_BOARD - 1)
			res[ti][tj] = -2;
		else
			res[ti][tj] = temp;

		// if (temp > 0)
		// amountBlacks--;
		// else
		// amountWhites--;

		res[mi][mj] = 0;
		lastAtackI = ti;
		lastAtackJ = tj;
		afterHit = true;
		return res;
	}

	public byte[][] BOARD() {
		return board;
	}

	private boolean inBounds(int i, int j) {
		if (i < 0 || j < 0 || i >= CT.SIZE_BOARD || j >= CT.SIZE_BOARD)
			return false;
		return true;
	}

	public void showNextMove() {
		for (MatrixBoard mb : nextTurns) {
			System.out.println(mb);
		}
	}

	public ArrayList<MatrixBoard> getNextMoves() {
		return nextTurns;
	}

	@Override
	public String toString() {
		String ss = "Move#" + countMoves;
		ss += "\nWHITE: " + white;
		ss += "\nG: " + countGames;
		ss += "\nW: " + countWins;
		ss += "\nL: " + countLoses;
		ss += "\n";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				ss += board[i][j] + " ";
			}
			ss += "\n";
		}
		return ss;
	}

	public static boolean isAfterHit() {
		return afterHit;
	}



	public boolean isEnemyHit() {
		return enemyHit;
	}

	

}
