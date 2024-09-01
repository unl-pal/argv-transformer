package com.jsyn.examples;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortControllerFactory;
import com.jsyn.unitgen.ImpulseOscillator;
import com.jsyn.unitgen.ImpulseOscillatorBL;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PulseOscillator;
import com.jsyn.unitgen.PulseOscillatorBL;
import com.jsyn.unitgen.RedNoise;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Display each oscillators waveform using the AudioScope. This is a
 * reimplementation of the TJ_SeeOsc Applet from the old API.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class SeeOscillators extends JApplet
{
	private static final long serialVersionUID = -8315903842197137926L;
	private Synthesizer synth;
	private ArrayList<UnitOscillator> oscillators = new ArrayList<UnitOscillator>();
	private LineOut lineOut;
	private AudioScope scope;
	private JPanel oscPanel;
	private Multiply oscGain;
	private ButtonGroup buttonGroup;
	private LinearRamp freqRamp;

}
