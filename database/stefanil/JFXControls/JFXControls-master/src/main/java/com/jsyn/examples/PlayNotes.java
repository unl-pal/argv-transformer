package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitVoice;
import com.softsynth.shared.time.TimeStamp;

/**
 * Play notes using timestamped noteOn and noteOff methods of the UnitVoice.
 * 
 * @author Phil Burk (C) 2009 Mobileer Inc
 * 
 */
public class PlayNotes
{
	Synthesizer synth;
	UnitGenerator ugen;
	UnitVoice voice;
	LineOut lineOut;
}
