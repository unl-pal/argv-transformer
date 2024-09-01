package com.jsyn.examples;

import java.awt.BorderLayout;

import javax.swing.JApplet;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.instruments.WaveShapingVoice;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.LineOut;
import com.jsyn.util.PseudoRandom;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.jsyn.EqualTemperedTuning;
import com.softsynth.shared.time.TimeStamp;

/***************************************************************
 * Play notes using a WaveShapingVoice. Allocate the notes using a
 * VoiceAllocator.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 */
public class ChebyshevSong extends JApplet implements Runnable
{
	private static final long serialVersionUID = -7459137388629333223L;
	private Synthesizer synth;
	private Add mixer;
	private LineOut lineOut;
	private AudioScope scope;
	private boolean go = false;
	private PseudoRandom pseudo = new PseudoRandom();
	private final static int MAX_VOICES = 8;
	private final static int MAX_NOTES = 5;
	private VoiceAllocator allocator;
	private final static int scale[] = { 0, 2, 4, 7, 9 }; // pentatonic scale

	public void run()
	{
		// always choose a new song based on time&date
		int savedSeed = (int) System.currentTimeMillis();
		// calculate tempo
		double duration = 0.2;
		// set time ahead of any system latency
		double advanceTime = 0.5;
		// time for next note to start
		double nextTime = synth.getCurrentTime() + advanceTime;
		// note is ON for half the duration
		double onTime = duration / 2;
		int beatIndex = 0;
		try
		{
			do
			{
				// on every measure, maybe repeat previous pattern
				if( (beatIndex & 7) == 0 )
				{
					if( (Math.random() < (1.0 / 2.0)) )
						pseudo.setSeed( savedSeed );
					else if( (Math.random() < (1.0 / 2.0)) )
						savedSeed = pseudo.getSeed();
				}

				// Play a bunch of random notes in the scale.
				int numNotes = pseudo.choose( MAX_NOTES );
				for( int i = 0; i < numNotes; i++ )
				{
					int noteNumber = pseudo.choose( 30 );
					noteOn( nextTime, noteNumber );
					noteOff( nextTime + onTime, noteNumber );
				}
		
				nextTime += duration;
				beatIndex += 1;

				// wake up before we need to play note to cover system latency
				synth.sleepUntil( nextTime - advanceTime );
			} while( go );
		} catch( InterruptedException e )
		{
			System.err.println( "Song exiting. " + e );
		}
	}
}
