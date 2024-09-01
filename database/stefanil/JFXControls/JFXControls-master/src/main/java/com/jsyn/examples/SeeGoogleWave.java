package com.jsyn.examples;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortControllerFactory;
import com.jsyn.unitgen.LineOut;

/**
 * Generate the waveform shown on the Google home page on 2/22/12.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class SeeGoogleWave extends JApplet
{
	private static final long serialVersionUID = -831590388347137926L;
	private Synthesizer synth;
	private GoogleWaveOscillator googleWaveUnit;
	private LineOut lineOut;
	private AudioScope scope;

}
