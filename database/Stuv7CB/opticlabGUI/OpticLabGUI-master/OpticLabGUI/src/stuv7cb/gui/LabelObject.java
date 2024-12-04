package stuv7cb.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

abstract class LabelObject extends JLabel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8613616493043654911L;
	protected int clickX;
	protected int clickY;
	protected int ID;
	private MouseListener ml;
	private MouseMotionListener mml;
	protected JPopupMenu popup=new JPopupMenu();
}
