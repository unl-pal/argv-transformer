package main;

import static rescuecore2.misc.java.JavaTools.instantiate;

import rescuecore2.messages.control.KVTimestep;
import rescuecore2.view.ViewComponent;
import rescuecore2.view.ViewListener;
import rescuecore2.view.RenderedObject;
import rescuecore2.score.ScoreFunction;
import rescuecore2.Constants;
import rescuecore2.Timestep;

import rescuecore2.standard.view.AnimatedWorldModelViewer;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.List;
import java.text.NumberFormat;

import rescuecore2.standard.components.StandardViewer;

/**
 * A simple viewer.
 */
public class SampleViewer extends StandardViewer {
	private static final int DEFAULT_FONT_SIZE = 20;
	private static final int PRECISION = 3;

	private static final String FONT_SIZE_KEY = "viewer.font-size";
	private static final String MAXIMISE_KEY = "viewer.maximise";
	private static final String TEAM_NAME_KEY = "viewer.team-name";

	private ScoreFunction scoreFunction;
	private ViewComponent viewer;
	private JLabel timeLabel;
	private JLabel scoreLabel;
	private JLabel teamLabel;
	private JLabel mapLabel;
	private NumberFormat format;
}
