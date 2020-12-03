/** filtered and transformed by PAClab */
package graphics.bas;

import gov.nasa.jpf.symbc.Debug;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Tile {

	/** PACLab: suitable */
	 private void showPosibleMove() {
		int J = Debug.makeSymbolicInteger("x1");
		int I = Debug.makeSymbolicInteger("x0");
		int ai = I - 1;
		int aj = J + 1;

		int bi = I - 1;
		int bj = J - 1;

		if (aj < Debug.makeSymbolicInteger("x2") && ai > 0 && Debug.makeSymbolicInteger("x3") == -1) {
		}

		if (bj < Debug.makeSymbolicInteger("x4") && bi > 0 && Debug.makeSymbolicInteger("x5") == -1) {
		}

		if (ai < Debug.makeSymbolicInteger("x6") && aj > 0 && Debug.makeSymbolicInteger("x7") == 0) {
		}
		if (bi < Debug.makeSymbolicInteger("x8") && bj > 0 && Debug.makeSymbolicInteger("x9") == 0) {
		}

	}

}
