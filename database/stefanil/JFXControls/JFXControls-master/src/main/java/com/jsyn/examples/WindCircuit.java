package com.jsyn.examples;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.MultiplyAdd;
import com.jsyn.unitgen.RedNoise;
import com.jsyn.unitgen.UnitSource;
import com.jsyn.unitgen.WhiteNoise;

/**
 * Wind Sound Create a wind-like sound by feeding white noise "shshshshsh"
 * through a randomly varying state filter to make a "whooowhoosh" sound. The
 * cuttoff frequency of the low pass filter is controlled by a RedNoise unit
 * which creates a slowly varying random control signal.
 * 
 * @author (C) 1997 Phil Burk, SoftSynth.com, All Rights Reserved
 */

public class WindCircuit extends Circuit implements UnitSource
{
	/* Declare units that will be part of the circuit. */
	WhiteNoise myNoise;
	FilterStateVariable myFilter;
	RedNoise myLFO;
	MultiplyAdd myScalar;

	/* Declare ports. */
	public UnitInputPort noiseAmp;
	public UnitInputPort modRate;
	public UnitInputPort modDepth;
	public UnitInputPort cutoff;
	public UnitInputPort resonance;
	public UnitInputPort amplitude;
	public UnitOutputPort output;
}
