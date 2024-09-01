package com.jsyn.examples;

import java.io.IOException;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.javasound.MidiDeviceTools;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.midi.MessageParser;
import com.jsyn.midi.MidiConstants;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PowerOfTwo;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;

/**
 * Connect a USB MIDI Keyboard to the internal MIDI Synthesizer
 * using JavaSound.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 * 
 */
public class UseMidiKeyboard
{
	private static final int MAX_VOICES = 8;
	private Synthesizer synth;
	private VoiceAllocator allocator;
	private LineOut lineOut;
	private double vibratoRate = 5.0;
	private double vibratoDepth = 0.0;

	private UnitOscillator lfo;
	private PowerOfTwo powerOfTwo;
	private MessageParser messageParser;
	private SubtractiveSynthVoice[] voices;

	// Write a Receiver to get the messages from a Transmitter.
	class CustomReceiver implements Receiver
	{
	}

	class MyParser extends MessageParser
	{
	}

}
