package com.example;

import com.example.missing.CurveTable;

/**
 * Suitable: every method has typed (int) parameters and does int arithmetic in
 * a conditional. Note the import of a class that does not exist in this repo --
 * the transformer has to deal with it before this file will compile.
 */
public class Grading {

	private final CurveTable curve = new CurveTable();

	public int letterCutoff(int score, int classSize) {
		int adjusted = score + classSize / 10;
		int cutoff = curve.bonusFor(classSize);
		if (adjusted > cutoff) {
			return 4;
		} else if (adjusted > 80) {
			return 3;
		}
		return 0;
	}
}
