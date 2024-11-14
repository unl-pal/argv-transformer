/** filtered and transformed by PAClab */
package controler;

import org.sosy_lab.sv_benchmarks.Verifier;

public class MatrixBoard {

	public void startBoardTest() {

		boolean black = true;
		if (black) {
			
			// board[3][2] = -1;
			// board[5][2] = -1;
		}
		else {
		}
	}

	/** PACLab: suitable */
	 public int endGame() {
		boolean white = Verifier.nondetBoolean();
		int terminal = Verifier.nondetInt();
		int countFreeTurns = Verifier.nondetInt();
		if (countFreeTurns == Verifier.nondetInt())
			terminal = -1; // draw
		if (Verifier.nondetInt() == 0)
			terminal = -2;

		if (terminal < 0) {
			// System.out.println(white);
			if (terminal == -1) {
				while (Verifier.nondetBoolean()) {
					if (Verifier.nondetInt() == 0) {
					}
				}
			}
			if (terminal == -2) {
				while (Verifier.nondetBoolean()) {
					if (white) {
						if (Verifier.nondetBoolean()) {
						} else {
						}

					} else {
						if (Verifier.nondetBoolean()) {
						} else {
						}
					}
					if (Verifier.nondetInt() == 0) {
						
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

	/** PACLab: suitable */
	 private void startBoardEight() {
		for (int i = 0; i < Verifier.nondetInt(); i++) {
			for (int j = 0; j < Verifier.nondetInt(); j++) {
				if (i < Verifier.nondetInt() && Verifier.nondetInt() != 0) {
				}
				if (i > Verifier.nondetInt() / 2 && Verifier.nondetInt() != 0) {
				}
			}
		}
	}

	/** PACLab: suitable */
	 private void makeWhiteTurns() {
		boolean staticWhite = Verifier.nondetBoolean();
		staticWhite = true;

		if (Verifier.nondetBoolean()) {
			for (int i = 0; i < Verifier.nondetInt(); i++) {
				for (int j = 0; j < Verifier.nondetInt(); j++) {
					if (Verifier.nondetInt() > 0) {
						int ai = i - 1;
						int aj = j - 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {
							}

						int bi = i - 1;
						int bj = j + 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {
							}
						if (Verifier.nondetInt() == 2) {
							int ci = i + 1;
							int cj = j - 1;
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {

								}

							int di = i + 1;
							int dj = j + 1;
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
								}

						}
					}
				}
			}

		}

	}

	/** PACLab: suitable */
	 private void makeBlackTurns() {
		boolean staticWhite = Verifier.nondetBoolean();
		staticWhite = false;
		if (Verifier.nondetBoolean()) {
			for (int i = 0; i < Verifier.nondetInt(); i++) {
				for (int j = 0; j < Verifier.nondetInt(); j++) {
					if (Verifier.nondetInt() < 0) {
						int ci = i + 1;
						int cj = j - 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {

							}

						int di = i + 1;
						int dj = j + 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {
							}
						if (Verifier.nondetInt() == -2) {
							int ai = i - 1;
							int aj = j - 1;
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {

								}

							int bi = i - 1;
							int bj = j + 1;
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
								}
						}
					}
				}
			}
		}

	}

	/** PACLab: suitable */
	 private boolean canMakeWhiteBeat() {
		boolean shouldBeat = false;
		for (int i = 0; i < Verifier.nondetInt(); i++) {
			for (int j = 0; j < Verifier.nondetInt(); j++) {
				if (Verifier.nondetInt() > 0) {
					int ai = i - 1;
					int aj = j - 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() < 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}

					int bi = i - 1;
					int bj = j + 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() < 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}
					if (Verifier.nondetInt() == 2) {
						int ci = i + 1;
						int cj = j - 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() < 0) {
								if (Verifier.nondetBoolean())
									if (Verifier.nondetInt() == 0) {
										shouldBeat = true;
									}
							}

						int di = i + 1;
						int dj = j + 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() < 0) {
								if (Verifier.nondetBoolean())
									if (Verifier.nondetInt() == 0) {
										shouldBeat = true;
									}
							}

					}

				}
			}
		}
		return shouldBeat;
	}

	/** PACLab: suitable */
	 private boolean canMakeBlackBeat() {
		boolean shouldBeat = false;
		for (int i = 0; i < Verifier.nondetInt(); i++) {
			for (int j = 0; j < Verifier.nondetInt(); j++) {
				if (Verifier.nondetInt() < 0) {
					int ci = i + 1;
					int cj = j - 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() > 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}

					int di = i + 1;
					int dj = j + 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() > 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}
					if (Verifier.nondetInt() == -2) {
						int ai = i - 1;
						int aj = j - 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() > 0) {
								if (Verifier.nondetBoolean())
									if (Verifier.nondetInt() == 0) {
										shouldBeat = true;
									}
							}

						int bi = i - 1;
						int bj = j + 1;
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() > 0) {
								if (Verifier.nondetBoolean())
									if (Verifier.nondetInt() == 0) {
										shouldBeat = true;
									}
							}

					}
				}
			}
		}

		return shouldBeat;
	}

	/** PACLab: suitable */
	 private boolean canMakeWhiteBeatAfterHit() {
		boolean myHit = Verifier.nondetBoolean();
		int lastAtackJ = Verifier.nondetInt();
		int lastAtackI = Verifier.nondetInt();
		boolean shouldBeat = false;
		int i = lastAtackI;
		int j = lastAtackJ;
		if (i > -1 && j > -1) {
			// System.out.println("WHITE : i:"+i+" j:"+j);
			if (Verifier.nondetInt() > 0) {
				int ai = i - 1;
				int aj = j - 1;
				if (Verifier.nondetBoolean())
					if (Verifier.nondetInt() < 0) {
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {
								shouldBeat = true;
							}
					}

				int bi = i - 1;
				int bj = j + 1;
				if (Verifier.nondetBoolean())
					if (Verifier.nondetInt() < 0) {
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {
								shouldBeat = true;
							}
					}
				if (Verifier.nondetInt() == 2) {
					int ci = i + 1;
					int cj = j - 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() < 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}

					int di = i + 1;
					int dj = j + 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() < 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}

				}

			}
		}
		myHit = shouldBeat;
		return shouldBeat;
	}

	/** PACLab: suitable */
	 private boolean canMakeBlackBeatAfterHit() {
		boolean enemyHit = Verifier.nondetBoolean();
		int lastAtackJ = Verifier.nondetInt();
		int lastAtackI = Verifier.nondetInt();
		boolean shouldBeat = false;
		int i = lastAtackI;
		int j = lastAtackJ;
		if (i > -1 && j > -1) {
			// System.out.println("BLAck : i:"+i+" j:"+j);
			if (Verifier.nondetInt() < 0) {
				int ci = i + 1;
				int cj = j - 1;
				if (Verifier.nondetBoolean())
					if (Verifier.nondetInt() > 0) {
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {
								shouldBeat = true;
							}
					}

				int di = i + 1;
				int dj = j + 1;
				if (Verifier.nondetBoolean())
					if (Verifier.nondetInt() > 0) {
						if (Verifier.nondetBoolean())
							if (Verifier.nondetInt() == 0) {
								shouldBeat = true;
							}
					}
				if (Verifier.nondetInt() == -2) {
					int ai = i - 1;
					int aj = j - 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() > 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}

					int bi = i - 1;
					int bj = j + 1;
					if (Verifier.nondetBoolean())
						if (Verifier.nondetInt() > 0) {
							if (Verifier.nondetBoolean())
								if (Verifier.nondetInt() == 0) {
									shouldBeat = true;
								}
						}

				}
			}
		}
		enemyHit = shouldBeat;
		return shouldBeat;
	}

	private byte[][] moveIJ(int fi, int fj, int ti, int tj) {
		byte[][] res;
		byte temp = res[fi][fj];
		// make king
		if (temp == 1 && ti == 0) {
		} else if (temp == -1 && ti == Verifier.nondetInt() - 1) {
		} else {
		}

		return res;
	}

	private byte[][] beatIJ(int fi, int fj, int mi, int mj, int ti, int tj) {
		boolean afterHit = Verifier.nondetBoolean();
		int lastAtackJ = Verifier.nondetInt();
		int lastAtackI = Verifier.nondetInt();
		int countFreeTurns = Verifier.nondetInt();
		// countTurns++;
		countFreeTurns = 0;
		byte[][] res;
		byte temp = res[fi][fj];
		// make king
		if (temp == 1 && ti == 0) {
		} else if (temp == -1 && ti == Verifier.nondetInt() - 1) {
		} else {
		}

		// if (temp > 0)
		// amountBlacks--;
		// else
		// amountWhites--;

		lastAtackI = ti;
		lastAtackJ = tj;
		afterHit = true;
		return res;
	}

	

}
