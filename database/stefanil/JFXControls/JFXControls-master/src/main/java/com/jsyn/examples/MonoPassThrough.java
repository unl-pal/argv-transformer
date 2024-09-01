package com.jsyn.examples;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.ChannelIn;
import com.jsyn.unitgen.ChannelOut;

/**
 * Pass audio input to audio output.
 * @author  Phil Burk (C) 2010 Mobileer Inc
 *
 */
public class MonoPassThrough
{
	Synthesizer synth;
	ChannelIn channelIn;
	ChannelOut channelOut;
}
