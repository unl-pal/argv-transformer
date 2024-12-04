package controler;

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

	

}
