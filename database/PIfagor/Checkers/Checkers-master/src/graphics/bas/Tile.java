package graphics.bas;

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

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constant.CS;
import constant.CT;
import constant.Side;
import controler.MatrixBoard;
import controler.Utils;

public class Tile extends JPanel implements MouseListener {

	private BufferedImage white;
	private BufferedImage black;
	private BufferedImage whiteKing;
	private BufferedImage blackKing;
	private BufferedImage possMove;
	private BufferedImage enemyTile;
	private BufferedImage currentTile;
	private static MatrixBoard game;
	private static byte[][] board;
	private int I;
	private int J;
	private int ID;

	private static int currentID = 0;
	private static boolean check = false;
	private static int tempI;
	private static int tempJ;
	private static Color lastColor;

	private void showPosibleMove() {
		int ai = I - 1;
		int aj = J + 1;

		int bi = I - 1;
		int bj = J - 1;

		if (aj < CT.SIZE_BOARD && ai > 0 && board[ai][aj] == -1) {
			this.getParent().getComponents()[fXYtID(ai, aj)]
					.setBackground(CS.MAROON);
			showPosibleMove(ai, aj, Side.UPRIGTH);
		}

		if (bj < CT.SIZE_BOARD && bi > 0 && board[bi][bj] == -1) {
			this.getParent().getComponents()[fXYtID(bi, bj)]
					.setBackground(CS.MAROON);
			showPosibleMove(bi, bj, Side.UPLEFT);
		}

		if (ai < CT.SIZE_BOARD && aj > 0 && board[ai][aj] == 0)
			this.getParent().getComponents()[fXYtID(ai, aj)]
					.setBackground(CS.GOLD);
		if (bi < CT.SIZE_BOARD && bj > 0 && board[bi][bj] == 0)//
			this.getParent().getComponents()[fXYtID(bi, bj)]
					.setBackground(CS.GREY);

	}

}
