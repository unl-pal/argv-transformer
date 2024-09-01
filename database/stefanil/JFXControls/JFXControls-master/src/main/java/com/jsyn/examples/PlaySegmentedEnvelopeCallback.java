package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.ports.QueueDataCommand;
import com.jsyn.ports.QueueDataEvent;
import com.jsyn.ports.UnitDataQueueCallback;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;

/**
 * Use a UnitDataQueueCallback to notify us of the envelope's progress.
 * Modulate the amplitude of an oscillator using a segmented envelope.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class PlaySegmentedEnvelopeCallback
{
	private Synthesizer synth;
	private UnitOscillator osc;
	private LineOut lineOut;
	private SegmentedEnvelope envelope;
	private VariableRateDataReader envelopePlayer;

	class TestQueueCallback implements UnitDataQueueCallback
	{
	}
}
