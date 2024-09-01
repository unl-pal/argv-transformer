package com.jsyn.examples;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.EdgeDetector;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.Latch;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.PulseOscillator;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitSource;

/**
 * Classic osc-filter-envelope voice with a sample and hold.
 * 
 * @author Phil Burk (C) 2011 Mobileer Inc
 *
 */
public class SampleHoldNoteBlaster extends Circuit implements UnitSource
{

	public UnitInputPort frequency;
	public UnitInputPort amplitude;
	public UnitInputPort modRate;
	public UnitInputPort modDepth;
	private UnitInputPort cutoff;
	private UnitInputPort resonance;
	private UnitInputPort pulseRate;
	private UnitInputPort sweepRate;
	private UnitInputPort sweepDepth;
	public UnitOutputPort output;

	private static SampleHoldNoteBlaster soundMaker; // singleton

	private UnitOscillator osc;
	private UnitOscillator samplee; // for sample and hold
	private PulseOscillator pulser;
	private Latch latch;
	private UnitOscillator lfo;
	private FilterLowPass filter;
	private PassThrough frequencyPin;
	private Multiply modScaler;
	private EnvelopeDAHDSR ampEnv;
	private Multiply sweepScaler;
	private EdgeDetector edgeDetector;
	private UnitInputPort pulseWidth;
	private UnitInputPort attack;
	private UnitInputPort decay;
	private UnitInputPort sustain;
	private UnitInputPort release;
}
