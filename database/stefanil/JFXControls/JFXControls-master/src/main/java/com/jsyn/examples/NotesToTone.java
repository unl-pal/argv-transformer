/**
 * If you play notes fast enough they become a tone.
 * 
 * Play a sine wave modulated by an envelope.
 * Speed up the envelope until it is playing at audio rate.
 * Slow down the oscillator until it becomes an LFO amp modulator.
 * Use a LatchZeroCrossing to stop at the end of a sine wave cycle when we are finished.
 *
 * @author Phil Burk, (C) 2010 Mobileer Inc, All Rights Reserved
 */

package com.jsyn.examples;

import java.io.File;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.unitgen.ExponentialRamp;
import com.jsyn.unitgen.LatchZeroCrossing;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.util.WaveRecorder;

/**
 * When notes speed up they can become a new tone.
 * <br>
 * Multiply an oscillator and an envelope.
 * Speed up the envelope until it becomes a tone.
 * Slow down the oscillator until it acts like an envelope.
 * Write the resulting audio to a WAV file.
 * 
 * @author  Phil Burk (C) 2011 Mobileer Inc
 *
 */

public class NotesToTone
{
	private final static double SONG_AMPLITUDE = 0.7;
	private final static double INTRO_DURATION = 2.0;
	private final static double OUTRO_DURATION = 2.0;
	private final static double RAMP_DURATION = 20.0;
	private final static double LOW_FREQUENCY = 1.0;
	private final static double HIGH_FREQUENCY = 800.0;

	private final static boolean useRecorder = true;
	private WaveRecorder recorder;

	private Synthesizer synth;
	private ExponentialRamp envSweeper;
	private ExponentialRamp oscSweeper;
	private VariableRateDataReader envelopePlayer;
	private UnitOscillator osc;
	private LatchZeroCrossing latch;
	private LineOut lineOut;
	private SegmentedEnvelope envelope;

}
