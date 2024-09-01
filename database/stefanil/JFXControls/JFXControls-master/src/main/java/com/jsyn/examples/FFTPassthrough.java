package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FFT;
import com.jsyn.unitgen.IFFT;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SpectralFFT;
import com.jsyn.unitgen.SpectralIFFT;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Play a sine sweep through an FFT/IFFT pair.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class FFTPassthrough
{
	private Synthesizer synth;
	private PassThrough center;
	private UnitOscillator osc;
	private UnitOscillator lfo;
	private SpectralFFT fft;
	private SpectralIFFT ifft1;
	private LineOut lineOut;
	private SpectralIFFT ifft2;
}
