/*
 *
 *    Permission to use, copy, modify, and/or distribute this software for any
 *    purpose with or without fee is hereby granted, provided that the above
 *    copyright notice and this permission notice appear in all copies.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *    WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *    MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *    ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *    WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *    ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *    OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package org.alljoyn.bus.sample.chat;

import org.alljoyn.bus.sample.chat.AllJoynService;
import org.alljoyn.bus.sample.chat.Observable;
import org.alljoyn.bus.sample.chat.Observer;
import org.alljoyn.bus.sample.chat.AllJoynService.UseChannelState;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.os.SystemClock;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * The ChatAppliation class serves as the Model (in the sense of the common
 * user interface design pattern known as Model-View-Controller) for the chat
 * application. 
 *
 * The ChatApplication inherits from the relatively little-known Android 
 * application framework class Application.  From the Android developers
 * reference on class Application:
 * 
 *   Base class for those who need to maintain global application state.
 *   You can provide your own implementation by specifying its name in your
 *   AndroidManifest.xml's <application> tag, which will cause that class to
 *   be instantiated for you when the process for your application/package is
 *   created.
 *   
 * The important property of class Application is that its lifetime coincides
 * with the lifetime of the application, not its activities.  Since we have
 * persistent state in our connections to the outside world via our AllJoyn
 * objects, and that state cannot be serialized, saved and restored; we need
 * a persistent object to ensure that state is held if transient objects like
 * Activities are destroyed and recreated by the Android application framework
 * during its normal operation.
 * 
 * This object holds the global state for our chat application, and starts the
 * Android Service that handles the background processing relating to our
 * AllJoyn connections.
 * 
 * Additionally, this class provides the Model for an MVC framework.  It 
 * provides a relatively abstract idea of what it is the application is doing.
 * For example, we provide methods oriented to conceptual actions (like our
 * user has typed a message) instead of methods oriented to the implementation
 * (like, create an AllJoyn bus object and register it).  This allows the
 * user interface to be relatively independent of the channel implementation.
 * 
 * Android Activities can come and go in sometimes surprising ways during the
 * operation of an application.  For example, when a phone is rotated from
 * portrait to landscape orientation, the displayed Activities are deleted
 * and recreated in the new orientation.  This class holds the persistent
 * state that is required to correctly display Activities when they are
 * recreated.
 */
public class ChatApplication extends Application implements Observable {
    private static final String TAG = "chat.ChatApplication";
    public static String PACKAGE_NAME;
    
    private static String hostRandomChannel;
    private static final String HOST_CHANNEL_PREFIX = "Chatnonymous";     
	ComponentName mRunningService = null;
    
    public static final String APPLICATION_QUIT_EVENT = "APPLICATION_QUIT_EVENT";
		
	/**
	 * The high-level module that caught the last AllJoyn error.
	 */
	private Module mModule = Module.NONE;
		
	/**
	 * Enumeration of the high-level moudules in the system.  There is one
	 * value per module.
	 */
	public static enum Module {
		NONE,
		GENERAL,
		USE,
		HOST
	}
		
	/**
	 * The string representing the last AllJoyn error that happened in the 
	 * AllJoyn Service.
	 */
	private String mErrorString = "ER_OK";

	/**
	 * The object we use in notifications to indicate that an AllJoyn error has
	 * happened.
	 */
	public static final String ALLJOYN_ERROR_EVENT = "ALLJOYN_ERROR_EVENT";
	
    /**
	 * The channels list is the list of all well-known names that correspond to
	 * channels we might conceivably be interested in.  We expect that the
	 * "use" GUID will allow the local user to have this list displayed in a
	 * "join channel" dialog, whereupon she will choose one.  This will
	 * eventually result in a joinSession call out from the AllJoyn Service 
	 */
	private List<String> mChannels = new ArrayList<String>();
    
    /**
     * The application has three ideas about the state of its channels.  This
     * is very detailed for a real application, but since this is an AllJoyn
     * sample, we think it is important to convey the detailed state back to
     * our user, whom we assume knows what it all means.
     * 
     * We have a basic bus attachment state, which reflects the fact that we
     * can't do anything wihtout a bus attachment.  When the service comes up
     * it automatically connects and starts discovering other instances of the
     * application, so this isn't terribly interesting.
	 */
	public AllJoynService.BusAttachmentState mBusAttachmentState = AllJoynService.BusAttachmentState.DISCONNECTED;

	/** 
     * The "host" state which reflects the state of the part of the system
     * related to hosting an chat channel.  In a "real" application this kind
     * of detail probably isn't appropriate, but we want to do so for this
     * sample.
	 */
	private AllJoynService.HostChannelState mHostChannelState = AllJoynService.HostChannelState.IDLE;
	
	/** 
     * The name of the "host" channel which the user has selected.
	 */
	private String mHostChannelName;
	
	/**
	 * The object we use in notifications to indicate that the state of the
	 * "host" channel or its name has changed.
	 */
	public static final String HOST_CHANNEL_STATE_CHANGED_EVENT = "HOST_CHANNEL_STATE_CHANGED_EVENT";

	/** 
     * The "use" state which reflects the state of the part of the system
     * related to using a remotely hosted chat channel.  In a "real" application
     * this kind of detail probably isn't appropriate, but we want to do so for
     * this sample.
	 */
	private AllJoynService.UseChannelState mUseChannelState = AllJoynService.UseChannelState.IDLE;
	
	/** 
     * The name of the "use" channel which the user has selected.
	 */
	private String mUseChannelName;
	
	/**
	 * The object we use in notifications to indicate that the state of the
	 * "use" channel or its name has changed.
	 */
	public static final String USE_CHANNEL_STATE_CHANGED_EVENT = "USE_CHANNEL_STATE_CHANGED_EVENT";
	
	/**
	 * The object we use in notifications to indicate that user has requested
	 * that we join a channel in the "use" tab.
	 */
	public static final String USE_JOIN_CHANNEL_EVENT = "USE_JOIN_CHANNEL_EVENT";
	
	/**
	 * The object we use in notifications to indicate that user has requested
	 * that we leave a channel in the "use" tab.
	 */
	public static final String USE_LEAVE_CHANNEL_EVENT = "USE_LEAVE_CHANNEL_EVENT";
	
	/**
	 * The object we use in notifications to indicate that user has requested
	 * that we initialize the host channel parameters in the "use" tab.
	 */
	public static final String HOST_INIT_CHANNEL_EVENT = "HOST_INIT_CHANNEL_EVENT";
	
	/**
	 * The object we use in notifications to indicate that user has requested
	 * that we initialize the host channel parameters in the "use" tab.
	 */
	public static final String HOST_START_CHANNEL_EVENT = "HOST_START_CHANNEL_EVENT";
	
	/**
	 * The object we use in notifications to indicate that user has requested
	 * that we initialize the host channel parameters in the "use" tab.
	 */
	public static final String HOST_STOP_CHANNEL_EVENT = "HOST_STOP_CHANNEL_EVENT";
	
	private AppStatus mAppStatus = AppStatus.IDLE;
	
	public static enum AppStatus {
		IDLE,
		SEEKING_CONNECTION_ACTIVE, // when we are actively seeking a connection by cycling through available channels
		SEEKING_CONNECTION_INACTIVE, // when no channels are valid (i.e. our app has the highest channel number), but we still want a connection
		CONNECTED
	}
	
	private String mAppSessionName;
	private int mAppSessionId;
	
	public static final String CONNECTION_ATTEMPTED_EVENT = "CONNECTION_ATTEMPTED_EVENT";
	public static final String CONNECTION_ESTABLISHED_EVENT = "CONNECTION_ESTABLISHED_EVENT";
	public static final String SET_DEFAULTS_EVENT = "SET_DEFAULTS_EVENT";
	
	final int OUTBOUND_MAX = 5;
	
	/**
	 * The object we use in notifications to indicate that the the user has
	 * entered a message and it is queued to be sent to the outside world.
	 */
	public static final String OUTBOUND_CHANGED_EVENT = "OUTBOUND_CHANGED_EVENT";
	
	/**
	 * The outbound list is the list of all messages that have been originated
	 * by our local user and are designed for the outside world.
	 */
	private List<String> mOutbound = new ArrayList<String>();
	
	/**
	 * The object we use in notifications to indicate that the history state of
	 * the model has changed and observers need to synchronize with it.
	 */
	public static final String HISTORY_CHANGED_EVENT = "HISTORY_CHANGED_EVENT";
	
	/**
	 * Don't keep an infinite amount of history.  Although we don't want to 
	 * admit it, this is a toy application, so we just keep a little history.
	 */
	final int HISTORY_MAX = 20;
	
	/**
	 * The history list is the list of all messages that have been originated
	 * or recieved by the "use" channel.
	 */
	private List<String> mHistory = new ArrayList<String>();
	
	/**
	 * The observers list is the list of all objects that have registered with
	 * us as observers in order to get notifications of interesting events.
	 */
	private List<Observer> mObservers = new ArrayList<Observer>();
}
