/*
 * Copyright (c) 2010-2011,2013, AllSeen Alliance. All rights reserved.
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

import org.alljoyn.bus.sample.chat.ChatApplication;
import org.alljoyn.bus.sample.chat.TabWidget;
import org.alljoyn.bus.sample.chat.Observable;
import org.alljoyn.bus.sample.chat.Observer;
import org.alljoyn.bus.sample.chat.ChatInterface;
import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.MessageContext;
import org.alljoyn.bus.SessionListener;
import org.alljoyn.bus.SessionPortListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.alljoyn.bus.SignalEmitter;
import org.alljoyn.bus.Status;
import org.alljoyn.bus.annotation.BusSignalHandler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class AllJoynService extends Service implements Observer {
	private static final String TAG = "chat.AllJoynService";

	private static final int NOTIFICATION_ID = 0xdefaced;
	
	/**
     * A reference to a descendent of the Android Application class that is
     * acting as the Model of our MVC-based application.
     */
     private ChatApplication mChatApplication = null;
	
    /**
     * This is the Android Service message handler.  It runs in the context of the
     * main Android Service thread, which is also shared with Activities since 
     * Android is a fundamentally single-threaded system.
     * 
     * The important thing for us is to note that this thread cannot be blocked for
     * a significant amount of time or we risk the dreaded "force close" message.
     * We can run relatively short-lived operations here, but we need to run our
     * distributed system calls in a background thread.
     * 
     * This handler serves translates from UI-related events into AllJoyn events
     * and decides whether functions can be handled in the context of the
     * Android main thread or if they must be dispatched to a background thread
     * which can take as much time as needed to accomplish a task.
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
	            case HANDLE_APPLICATION_QUIT_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): APPLICATION_QUIT_EVENT");
	                mBackgroundHandler.leaveSession();
	                mBackgroundHandler.cancelAdvertise();
	                mBackgroundHandler.unbindSession();
	                mBackgroundHandler.releaseName();
	                mBackgroundHandler.exit();
	                stopSelf();
	            }
	            break;            
            case HANDLE_USE_JOIN_CHANNEL_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): USE_JOIN_CHANNEL_EVENT");
	                mBackgroundHandler.joinSession();
	            }
	            break;
	        case HANDLE_USE_LEAVE_CHANNEL_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): USE_LEAVE_CHANNEL_EVENT");
	                mBackgroundHandler.leaveSession();
	            }
	            break;
	        case HANDLE_HOST_INIT_CHANNEL_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HOST_INIT_CHANNEL_EVENT");
	            }
	            break;	            
	        case HANDLE_HOST_START_CHANNEL_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HOST_START_CHANNEL_EVENT");
	                mBackgroundHandler.requestName();
	                mBackgroundHandler.bindSession();
	                mBackgroundHandler.advertise();
	            }
	            break;
	        case HANDLE_HOST_STOP_CHANNEL_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HOST_STOP_CHANNEL_EVENT");
	                mBackgroundHandler.cancelAdvertise();
	                mBackgroundHandler.unbindSession();
	                mBackgroundHandler.releaseName();
	            }
	            break;
	        case HANDLE_OUTBOUND_CHANGED_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): OUTBOUND_CHANGED_EVENT");
	                mBackgroundHandler.sendMessages();
	            }
	            break;
            default:
                break;
            }
        }
    };
    
    /**
     * Value for the HANDLE_APPLICATION_QUIT_EVENT case observer notification handler. 
     */
    private static final int HANDLE_APPLICATION_QUIT_EVENT = 0;
    
    /**
     * Value for the HANDLE_USE_JOIN_CHANNEL_EVENT case observer notification handler. 
     */
    private static final int HANDLE_USE_JOIN_CHANNEL_EVENT = 1;
    
    /**
     * Value for the HANDLE_USE_LEAVE_CHANNEL_EVENT case observer notification handler. 
     */
    private static final int HANDLE_USE_LEAVE_CHANNEL_EVENT = 2;
    
    /**
     * Value for the HANDLE_HOST_INIT_CHANNEL_EVENT case observer notification handler. 
     */
    private static final int HANDLE_HOST_INIT_CHANNEL_EVENT = 3;
    
    /**
     * Value for the HANDLE_HOST_START_CHANNEL_EVENT case observer notification handler. 
     */
    private static final int HANDLE_HOST_START_CHANNEL_EVENT = 4;
    
    /**
     * Value for the HANDLE_HOST_STOP_CHANNEL_EVENT case observer notification handler. 
     */
    private static final int HANDLE_HOST_STOP_CHANNEL_EVENT = 5;
    
    /**
     * Value for the HANDLE_OUTBOUND_CHANGED_EVENT case observer notification handler. 
     */
    private static final int HANDLE_OUTBOUND_CHANGED_EVENT = 6;
    
    /**
     * Enumeration of the states of the AllJoyn bus attachment.  This
     * lets us make a note to ourselves regarding where we are in the process
     * of preparing and tearing down the fundamental connection to the AllJoyn
     * bus.
     * 
     * This should really be a more private think, but for the sample we want
     * to show the user the states we are running through.  Because we are
     * really making a data hiding exception, and because we trust ourselves,
     * we don't go to any effort to prevent the UI from changing our state out
     * from under us.
     * 
     * There are separate variables describing the states of the client
     * ("use") and service ("host") pieces.
     */
    public static enum BusAttachmentState {
    	DISCONNECTED,	/** The bus attachment is not connected to the AllJoyn bus */ 
    	CONNECTED,		/** The  bus attachment is connected to the AllJoyn bus */
    	DISCOVERING		/** The bus attachment is discovering remote attachments hosting chat channels */
    }
    
    /**
     * The state of the AllJoyn bus attachment.
     */
    private BusAttachmentState mBusAttachmentState = BusAttachmentState.DISCONNECTED;
    
    /**
     * Enumeration of the states of a hosted chat channel.  This lets us make a
     * note to ourselves regarding where we are in the process of preparing
     * and tearing down the AllJoyn pieces responsible for providing the chat
     * service.  In order to be out of the IDLE state, the BusAttachment state
     * must be at least CONNECTED.
     */
    public static enum HostChannelState {
    	IDLE,	        /** There is no hosted chat channel */ 
    	NAMED,		    /** The well-known name for the channel has been successfully acquired */
    	BOUND,			/** A session port has been bound for the channel */
    	ADVERTISED,	    /** The bus attachment has advertised itself as hosting an chat channel */
    	CONNECTED       /** At least one remote device has connected to a session on the channel */
    }
    
    /**
     * The state of the AllJoyn components responsible for hosting an chat channel.
     */
    private HostChannelState mHostChannelState = HostChannelState.IDLE;
    
    /**
     * Enumeration of the states of a hosted chat channel.  This lets us make a
     * note to ourselves regarding where we are in the process of preparing
     * and tearing down the AllJoyn pieces responsible for providing the chat
     * service.  In order to be out of the IDLE state, the BusAttachment state
     * must be at least CONNECTED.
     */
    public static enum UseChannelState {
    	IDLE,	        /** There is no used chat channel */ 
    	JOINED,		    /** The session for the channel has been successfully joined */
    }
    
    /**
     * The state of the AllJoyn components responsible for hosting an chat channel.
     */
    private UseChannelState mUseChannelState = UseChannelState.IDLE;
    
    /**
     * This is the AllJoyn background thread handler class.  AllJoyn is a
     * distributed system and must therefore make calls to other devices over
     * networks.  These calls may take arbitrary amounts of time.  The Android
     * application framework is fundamentally single-threaded and so the main
     * Service thread that is executing in our component is the same thread as
     * the ones which appear to be executing the user interface code in the
     * other Activities.  We cannot block this thread while waiting for a
     * network to respond, so we need to run our calls in the context of a
     * background thread.  This is the class that provides that background
     * thread implementation.
     *
     * When we need to do some possibly long-lived task, we just pass a message
     * to an object implementing this class telling it what needs to be done.
     * There are two main parts to this class:  an external API and the actual
     * handler.  In order to make life easier for callers, we provide API
     * methods to deal with the actual message passing, and then when the
     * handler thread is executing the desired method, it calls out to an 
     * implementation in the enclosing class.  For example, in order to perform
     * a connect() operation in the background, the enclosing class calls
     * BackgroundHandler.connect(); and the result is that the enclosing class
     * method doConnect() will be called in the context of the background
     * thread.
     */
    private final class BackgroundHandler extends Handler {
    }
    
    private static final int EXIT = 1;
    private static final int CONNECT = 2;
    private static final int DISCONNECT = 3;
    private static final int START_DISCOVERY = 4;
    private static final int CANCEL_DISCOVERY = 5;
    private static final int REQUEST_NAME = 6;
    private static final int RELEASE_NAME = 7;
    private static final int BIND_SESSION = 8;
    private static final int UNBIND_SESSION = 9;
    private static final int ADVERTISE = 10;
    private static final int CANCEL_ADVERTISE = 11;
    private static final int JOIN_SESSION = 12;
    private static final int LEAVE_SESSION = 13;
    private static final int SEND_MESSAGES = 14;
    
    /**
     * The instance of the AllJoyn background thread handler.  It is created
     * when Android decides the Service is needed and is called from the
     * onCreate() method.  When Android decides our Service is no longer 
     * needed, it will call onDestroy(), which spins down the thread.
     */
    private BackgroundHandler mBackgroundHandler = null;
    
    /**
     * The bus attachment is the object that provides AllJoyn services to Java
     * clients.  Pretty much all communiation with AllJoyn is going to go through
     * this obejct.
     */
    private BusAttachment mBus  = new BusAttachment(ChatApplication.PACKAGE_NAME, BusAttachment.RemoteMessage.Receive);
    
    /**
     * The well-known name prefix which all bus attachments hosting a channel
     * will use.  The NAME_PREFIX and the channel name are composed to give
     * the well-known name a hosting bus attachment will request and 
     * advertise.
     */
    private static final String NAME_PREFIX = "org.alljoyn.bus.samples.chat";
    
	/**
	 * The well-known session port used as the contact port for the chat service.
	 */
    private static final short CONTACT_PORT = 27;
    
    /**
     * The object path used to identify the service "location" in the bus
     * attachment.
     */
    private static final String OBJECT_PATH = "/chatService";
    
    /**
     * The ChatBusListener is a class that listens to the AllJoyn bus for
     * notifications corresponding to the existence of events happening out on
     * the bus.  We provide one implementation of our listener to the bus
     * attachment during the connect(). 
     */
    private class ChatBusListener extends BusListener {
    }
    
    /**
     * An instance of an AllJoyn bus listener that knows what to do with
     * foundAdvertisedName and lostAdvertisedName notifications.  Although
     * we often use the anonymous class idiom when talking to AllJoyn, the
     * bus listener works slightly differently and it is better to use an
     * explicitly declared class in this case.
     */
    private ChatBusListener mBusListener = new ChatBusListener();
    
    // This semaphore prevents us from connecting to multiple sessions
    private final SessionGuardian mSessionGuardian = new SessionGuardian();
    
    private class SessionGuardian extends Semaphore{
    	private long timeAcquired;
    	private final long TIMEOUT_MS = 3000;
    }
    
    /**
     * The session identifier of the "host" session that the application
     * provides for remote devices.  Set to -1 if not connected.
     */
    int mHostSessionId = -1;
    
    /**
     * A flag indicating that the application has joined a chat channel that
     * it is hosting.  See the long comment in doJoinSession() for a
     * description of this rather non-intuitively complicated case.
     */
    boolean mJoinedToSelf = false;
    
    /**
     * This is the interface over which the chat messages will be sent in
     * the case where the application is joined to a chat channel hosted
     * by the application.  See the long comment in doJoinSession() for a
     * description of this rather non-intuitively complicated case.
     */
    ChatInterface mHostChatInterface = null;
    
    private HashMap<String, Long> mChannelConnectionTime = new HashMap<String, Long>();
    
    /**
     * This is the interface over which the chat messages will be sent.
     */
    ChatInterface mChatInterface = null;
    
    /**
     * The session identifier of the "use" session that the application
     * uses to talk to remote instances.  Set to -1 if not connectecd.
     */
    int mUseSessionId = -1;
    
    /**
     * Our chat messages are going to be Bus Signals multicast out onto an 
     * associated session.  In order to send signals, we need to define an
     * AllJoyn bus object that will allow us to instantiate a signal emmiter.
     */
    class ChatService implements ChatInterface, BusObject {     
    }

    /**
     * The ChatService is the instance of an AllJoyn interface that is exported
     * on the bus and allows us to send signals implementing messages
     */
    private ChatService mChatService = new ChatService();

    /*
     * Load the native alljoyn_java library.  The actual AllJoyn code is
     * written in C++ and the alljoyn_java library provides the language
     * bindings from Java to C++ and vice versa.
     */
    static {
        Log.i(TAG, "System.loadLibrary(\"alljoyn_java\")");
        System.loadLibrary("alljoyn_java");
    }
}
