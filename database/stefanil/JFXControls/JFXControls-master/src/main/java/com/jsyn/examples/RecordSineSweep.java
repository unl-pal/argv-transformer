/** 
 * Test recording to disk in non-real-time.
 * Play several frequencies of a sine wave.
 * Save data in a WAV file format.
 *
 * @author (C) 1997 Phil Burk, All Rights Reserved
 */

package com.jsyn.examples;

import java.io.File;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.ExponentialRamp;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.util.WaveRecorder;

public class RecordSineSweep
{
	final static double SONG_DURATION = 4.0;
	private Synthesizer synth;
	private UnitOscillator leftOsc;
	private UnitOscillator rightOsc;
	private ExponentialRamp sweeper;
	private LineOut lineOut;
	private WaveRecorder recorder;
	private final static boolean useRecorder = true;

}
