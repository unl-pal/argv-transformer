package com.jsyn.examples;

import java.io.File;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.ChannelIn;
import com.jsyn.unitgen.ChannelOut;
import com.jsyn.unitgen.FixedRateMonoReader;
import com.jsyn.unitgen.FixedRateMonoWriter;
import com.jsyn.unitgen.Maximum;
import com.jsyn.unitgen.Minimum;
import com.jsyn.util.WaveFileWriter;

/**
 * Record audio then play back.
 * @author  Phil Burk (C) 2010 Mobileer Inc
 *
 */
public class LongEcho
{
	final static int DELAY_SECONDS = 4;
	Synthesizer synth;
	ChannelIn channelIn;
	ChannelOut channelOut;
	FloatSample sample;
	FixedRateMonoReader reader;
	FixedRateMonoWriter writer;
	Minimum minner;
	Maximum maxxer;
}
