package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.AsymptoticRamp;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Pan;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitSource;

/**
 * Make a bunch of oscillators that swarm around a moving frequency.
 * 
 * @author Phil Burk (C) 2009 Mobileer Inc
 * 
 */
public class SwarmOfOscillators
{
	private Synthesizer synth;
	Follower[] followers;
	SineOscillator lfo;
	LineOut lineOut;
	private Add tiePoint;
	private static final int NUM_FOLLOWERS = 30;

	class Follower extends Circuit implements UnitSource
	{
		UnitOscillator osc;
		AsymptoticRamp lag;
		Pan panner;
	}
}
