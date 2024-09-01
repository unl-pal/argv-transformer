package com.jsyn.examples;

import java.awt.GridLayout;

import javax.swing.JApplet;
import javax.swing.JPanel;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.swing.ExponentialRangeModel;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortControllerFactory;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Play a sawtooth using a JSyn oscillator and some knobs.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class SawFaders extends JApplet
{
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	private UnitOscillator osc;
	private LinearRamp lag;
	private LineOut lineOut;

}
