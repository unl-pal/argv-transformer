package com.jsyn.examples;

import java.io.File;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.Spectrum;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.PinkNoise;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SpectralFilter;
import com.jsyn.unitgen.SpectralProcessor;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.WhiteNoise;
import com.jsyn.util.WaveRecorder;

/**
 * Play a sine sweep through an FFT/IFFT pair.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class HearSpectralFilter
{
	private Synthesizer synth;
	private PassThrough center;
	private UnitOscillator osc;
	private UnitOscillator lfo;
	private PassThrough mixer;
	private SpectralFilter filter;
	private LineOut lineOut;
	private WaveRecorder recorder;
	private final static boolean useRecorder = true;
	private final static boolean useProcessor = true;
	private final static int NUM_FFTS = 4;
	private final static int SIZE_LOG_2 = 9;
	private SpectralProcessor[] processors;
	private WhiteNoise noise;
	
	private static class CustomSpectralProcessor extends SpectralProcessor
	{
	}

	private void test() throws IOException
	{
		// Create a context for the synthesizer.
		synth = JSyn.createSynthesizer();
		synth.setRealTime( false );

		if( useRecorder )
		{
			File waveFile = new File( "temp_recording.wav" );
			// Default is stereo, 16 bits.
			recorder = new WaveRecorder( synth, waveFile );
			System.out.println( "Writing to WAV file "
					+ waveFile.getAbsolutePath() );
		}

		if( useProcessor )
		{
			processors = new SpectralProcessor[NUM_FFTS];
			for( int i = 0; i < NUM_FFTS; i++ )
			{
				processors[i] = new CustomSpectralProcessor();
			}
		}

		// Add a tone generator.
		synth.add( center = new PassThrough() );
		synth.add( lfo = new SineOscillator() );
		synth.add( noise = new WhiteNoise() );
		synth.add( mixer = new PassThrough() );

		// synth.add( osc = new SawtoothOscillatorBL() );
		synth.add( osc = new SineOscillator() );

		synth.add( filter = new SpectralFilter( NUM_FFTS, SIZE_LOG_2 ) );
		// Add a stereo audio output unit.
		synth.add( lineOut = new LineOut() );

		// Connect the oscillator to both channels of the output.
		center.output.connect( osc.frequency );
		lfo.output.connect( osc.frequency );
		osc.output.connect( mixer.input );
		noise.output.connect( mixer.input );
		mixer.output.connect( filter.input );
		if( useProcessor )
		{
			// Pass spectra through a custom processor.
			for( int i = 0; i < NUM_FFTS; i++ )
			{
				filter.getSpectralOutput( i ).connect( processors[i].input );
				processors[i].output.connect( filter.getSpectralInput( i ) );
			}
		}
		else
		{
			for( int i = 0; i < NUM_FFTS; i++ )
			{
				// Connect FFTs directly to IFFTs for passthrough.
				filter.getSpectralOutput( i ).connect(
						filter.getSpectralInput( i ) );
			}

		}
		filter.output.connect( 0, lineOut.input, 0 );
		filter.output.connect( 0, lineOut.input, 1 );

		// Set the frequency and amplitude for the modulated sine wave.
		center.input.set( 600.0 );
		lfo.frequency.set( 0.2 );
		lfo.amplitude.set( 400.0 );
		osc.amplitude.set( 0.2 );
		noise.amplitude.set( 0.05 );

		// Start synthesizer using default stereo output at 44100 Hz.
		synth.start();

		if( useRecorder )
		{
			mixer.output.connect( 0, recorder.getInput(), 0 );
			filter.output.connect( 0, recorder.getInput(), 1 );
			// When we start the recorder it will pull data from the oscillator
			// and sweeper.
			recorder.start();
		}

		// We only need to start the LineOut. It will pull data through the
		// chain.
		lineOut.start();

		System.out
				.println( "You should now be hearing a clean oscillator on the left channel," );
		System.out
				.println( "and the FFT->IFFT processed signal on the right channel." );

		// Sleep while the sound is generated in the background.
		try
		{
			double time = synth.getCurrentTime();
			// Sleep for a few seconds.
			synth.sleepUntil( time + 10.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}

		if( recorder != null )
		{
			recorder.stop();
			recorder.close();
		}

		System.out.println( "Stop playing. -------------------" );
		// Stop everything.
		synth.stop();
	}
}
