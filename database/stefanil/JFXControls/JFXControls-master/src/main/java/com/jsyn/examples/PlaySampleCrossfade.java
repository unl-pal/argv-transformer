package com.jsyn.examples;

import java.awt.GridLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.devices.AudioDeviceFactory;
import com.jsyn.ports.QueueDataCommand;
import com.jsyn.swing.DoubleBoundedRangeModel;
import com.jsyn.swing.DoubleBoundedRangeSlider;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortControllerFactory;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

/**
 * Play a sample from a WAV file using JSyn. Use a crossfade to play a loop at
 * an arbitrary position.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class PlaySampleCrossfade extends JApplet
{
	private static final double LOOP_START_FRACTION = 0.2;
	private Synthesizer synth;
	private VariableRateDataReader samplePlayer;
	private LineOut lineOut;
	private FloatSample sample;
	private DoubleBoundedRangeModel rangeModelSize;
	private DoubleBoundedRangeModel rangeModelCrossfade;
	private int loopStartFrame;

	private void queueNewLoop()
	{
		int loopSize = (int) (sample.getNumFrames() * rangeModelSize
				.getDoubleValue());
		if( (loopStartFrame + loopSize) > sample.getNumFrames() )
		{
			loopSize =  sample.getNumFrames() - loopStartFrame;
		}
		int crossFadeSize = (int) (rangeModelCrossfade.getDoubleValue());

		// For complex queuing operations, create a command and then customize it.
		QueueDataCommand command = samplePlayer.dataQueue
				.createQueueDataCommand( sample, loopStartFrame, loopSize );
		command.setNumLoops( -1 );
		command.setSkipIfOthers( true );
		command.setCrossFadeIn( crossFadeSize );

		System.out.println( "Queue: " + loopStartFrame + ", #" + loopSize
				+ ", X=" + crossFadeSize );
		synth.queueCommand( command );
	}

}
