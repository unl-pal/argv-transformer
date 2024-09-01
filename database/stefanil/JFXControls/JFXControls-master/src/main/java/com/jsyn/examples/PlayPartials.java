package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Play a enharmonic sine tones using JSyn oscillators.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class PlayPartials
{
	private Synthesizer synth;
	private UnitOscillator[] osc;
	private Multiply[] multipliers;
	private LinearRamp ramp;
	private LineOut lineOut;
	private double[] amps = { 0.2, 0.1, 0.3, 0.4 };
	private double[] ratios = { 1.0, Math.sqrt( 2.0 ), Math.E, Math.PI };
}
