package com.jsyn.examples;

import java.io.File;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.util.WaveRecorder;

/**
 * Modulate the amplitude of an oscillator using a segmented envelope.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class PlaySegmentedEnvelope
{
	private Synthesizer synth;
	private UnitOscillator osc;
	private LineOut lineOut;
	private SegmentedEnvelope envelope;
	private VariableRateDataReader envelopePlayer;
	private WaveRecorder recorder;
	private final static boolean useRecorder = true;
}
