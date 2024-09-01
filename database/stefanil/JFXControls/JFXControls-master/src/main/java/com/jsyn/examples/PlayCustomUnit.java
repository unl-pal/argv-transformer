package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Play a tone using a JSyn oscillator
 * and process it using a custom unit generator.
 * @author  Phil Burk (C) 2010 Mobileer Inc
 *
 */
public class PlayCustomUnit
{
	private Synthesizer synth;
	private UnitOscillator osc;
	private CustomCubeUnit cuber;
	private LineOut lineOut;
}
