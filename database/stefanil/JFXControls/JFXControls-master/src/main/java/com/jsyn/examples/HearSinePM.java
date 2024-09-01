package com.jsyn.examples;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.DoubleBoundedRangeModel;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SineOscillatorPhaseModulated;

/**
 * Play a tone using a phase modulated sinewave oscillator.
 * Phase modulation (PM) is very similar to frequency modulation (FM) but is easier to control.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class HearSinePM extends JApplet
{
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	SineOscillatorPhaseModulated carrier;
	SineOscillator modulator;
	LineOut lineOut;
	AudioScope scope;

}
