package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;
import com.syntona.exported.SawVoice;

/**
 * Play chords and melody using the VoiceAllocator.
 * 
 * @author Phil Burk (C) 2009 Mobileer Inc
 * 
 */
public class PlayChords
{
	private static final int MAX_VOICES = 8;
	private Synthesizer synth;
	private VoiceAllocator allocator;
	private LineOut lineOut;
	/** Number of seconds to generate music in advance of presentation-time. */
	private double advance = 0.2;
	private double secondsPerBeat = 0.6;
	// on time over note duration
	private double dutyCycle = 0.8;
	private double measure = secondsPerBeat * 4.0;
	private UnitVoice[] voices;
}
