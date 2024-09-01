package com.jsyn.examples;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JLabel;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;


/**
 * Display waveforms using the AudioScope.
 * The frequency of the oscillators is modulated by an LFO.
 * @author  Phil Burk (C) 2010 Mobileer Inc
 *
 */
public class ShowWaves extends JApplet
{
	private static final long serialVersionUID = -8315903842197137926L;
	private Synthesizer synth;
	private UnitOscillator lfo;
	private Add adder;
	private ArrayList<UnitOscillator> oscillators = new ArrayList<UnitOscillator>();
	private LineOut lineOut;
	private AudioScope scope;

}
