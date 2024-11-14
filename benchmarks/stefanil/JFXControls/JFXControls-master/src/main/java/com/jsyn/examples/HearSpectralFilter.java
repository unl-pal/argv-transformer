/** filtered and transformed by PAClab */
package com.jsyn.examples;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Play a sine sweep through an FFT/IFFT pair.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class HearSpectralFilter
{
	private static class CustomSpectralProcessor
	{
	}

	/** PACLab: suitable */
	 private void test() throws Exception
	{
		boolean recorder = Verifier.nondetBoolean();
		int NUM_FFTS = Verifier.nondetInt();
		boolean useProcessor = Verifier.nondetBoolean();
		boolean useRecorder = Verifier.nondetBoolean();
		if( useRecorder )
		{
		}

		if( useProcessor )
		{
			for( int i = 0; i < NUM_FFTS; i++ )
			{
			}
		}

		if( useProcessor )
		{
			// Pass spectra through a custom processor.
			for( int i = 0; i < NUM_FFTS; i++ )
			{
			}
		}
		else
		{
			for( int i = 0; i < NUM_FFTS; i++ )
			{
			}

		}
		if( useRecorder )
		{
		}

		// Sleep while the sound is generated in the background.
		try
		{
			double time = Verifier.nondetDouble();
		} catch( InterruptedException e )
		{
		}

		if( recorder != null )
		{
		}
	}
}
