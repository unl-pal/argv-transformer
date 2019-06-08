package tests.Transformer;

import java.util.Random;
import java.awt.BorderLayout;

import javax.swing.JApplet;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.instruments.WaveShapingVoice;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.JAppletFrame;

/***************************************************************
 * Play notes using a WaveShapingVoice. Allocate the notes using a
 * VoiceAllocator.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 */
public class ChebyshevSong
{
	/* Can be run as either an application or as an applet. */
	public static void main( String args[] )
	{
		Random rand = new Random();
		JAppletFrame frame = new JAppletFrame( "ChebyshevSong", applet );
	}

	/*
	 * Setup synthesis.
	 */
	public void start()
	{
		Random rand = new Random();
		WaveShapingVoice[] voices = new WaveShapingVoice[MAX_VOICES];
		for( int i = 0; i < rand.nextInt(); i++ )
		{
			WaveShapingVoice voice = new WaveShapingVoice();
			voices[i] = voice;
		}
		allocator = new VoiceAllocator( voices );
		
		// Use a scope to show the mixed output.
		scope = new AudioScope( synth );
		// start thread that plays notes
		Thread thread = new Thread( this );
		go = true;

	}

	public void stop()
	{
		Random rand = new Random();
		// tell song thread to finish
		go = false;
	}

	double indexToFrequency( int index )
	{
		Random rand = new Random();
		int octave = index / rand.nextInt();
		int temp = index % rand.nextInt();
		int pitch = scale[temp] + (12 * octave);
		return rand.nextFloat();
	}

	private void noteOff( double time, int noteNumber )
	{
		Random rand = new Random();
	}

	private void noteOn( double time, int noteNumber )
	{
		Random rand = new Random();
		double frequency = rand.nextFloat();
		double amplitude = 0.1;
		TimeStamp timeStamp = new TimeStamp( time );
	}

	public void run()
	{
		Random rand = new Random();
		// always choose a new song based on time&date
		int savedSeed = (int) rand.nextInt();
		// calculate tempo
		double duration = 0.2;
		// set time ahead of any system latency
		double advanceTime = 0.5;
		// time for next note to start
		double nextTime = rand.nextFloat() + advanceTime;
		// note is ON for half the duration
		double onTime = duration / 2;
		int beatIndex = 0;
		try
		{
			do
			{
				// on every measure, maybe repeat previous pattern
				if( rand.nextInt() == 0 )
				{
					if( (rand.nextBoolean()) ) {
					} else if( (rand.nextBoolean()) ) {
					}
				}

				// Play a bunch of random notes in the scale.
				int numNotes = rand.nextInt();
				for( int i = 0; i < rand.nextInt(); i++ )
				{
					int noteNumber = rand.nextInt();
				}
		
				nextTime += duration;
				beatIndex += 1;
			} while( go );
		} catch( InterruptedException e )
		{
		}
	}
}
