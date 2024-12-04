package ufrgs.maslab.abstractsimulator.core.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class Viewer extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7157083672751898254L;
	static final int width = 1000;
	static final int height = 750;
	static Environment<? extends Entity> env = null;
	static int time = 0;
	static JPanel area = null;


}
