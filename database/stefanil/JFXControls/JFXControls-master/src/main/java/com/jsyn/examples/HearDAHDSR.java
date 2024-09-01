package com.jsyn.examples;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JApplet;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.swing.DoubleBoundedRangeModel;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Play a tone using a JSyn oscillator. Modulate the amplitude using a DAHDSR
 * envelope.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class HearDAHDSR extends JApplet
{
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	private UnitOscillator osc;
	// Use a square wave to trigger the envelope.
	private UnitOscillator gatingOsc;
	private EnvelopeDAHDSR dahdsr;
	private LineOut lineOut;

}
