package ufrgs.maslab.abstractsimulator.core.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.values.Task;

public class TaskObject extends JPanel  implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4397072570513170778L;
	
	private int r = 0;
	private int g = 0;
	private int b = 0;
	
	private int x = 0;
	private int y = 0;
	private Task task = null;

}
