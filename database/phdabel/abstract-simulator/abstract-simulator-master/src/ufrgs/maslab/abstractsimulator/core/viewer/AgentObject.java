package ufrgs.maslab.abstractsimulator.core.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.variables.Agent;
import ufrgs.maslab.abstractsimulator.variables.FireFighter;
import ufrgs.maslab.abstractsimulator.variables.FireStation;


public class AgentObject extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1824456882221783249L;

	private int r = 0;
	private int g = 0;
	private int b = 0;
	
	private int x = 0;
	private int y = 0;
	private Agent agent = null;

}
