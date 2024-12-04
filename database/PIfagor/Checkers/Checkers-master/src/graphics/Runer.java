package graphics;

import graphics.bas.BasJMenuBar;
import graphics.bas.BasJPanel;
import graphics.bas.Tile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.swing.*;

import constant.CT;
import controler.Learning;
import controler.MatrixBoard;

public class Runer extends JFrame {
	private BasJMenuBar mb;
	private BasJPanel pn;
	private JMenuItem newGame;
	private JMenuItem restart;
	private JMenuItem endGame;
	private JMenuItem exitGame;

	private MatrixBoard mainBoard;

	public class MenuItemListener implements ActionListener {

	}

}
