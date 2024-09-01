/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;

import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

/**
 * Holds the list of messages to be sent to the WebView.
 */
public class NativeToJsMessageQueue {
    private static final String LOG_TAG = "JsMessageQueue";

    // Set this to true to force plugin results to be encoding as
    // JS instead of the custom format (useful for benchmarking).
    // Doesn't work for multipart messages.
    private static final boolean FORCE_ENCODE_USING_EVAL = false;

    // Disable sending back native->JS messages during an exec() when the active
    // exec() is asynchronous. Set this to true when running bridge benchmarks.
    static final boolean DISABLE_EXEC_CHAINING = false;

    // Arbitrarily chosen upper limit for how much data to send to JS in one shot.
    // This currently only chops up on message boundaries. It may be useful
    // to allow it to break up messages.
    private static int MAX_PAYLOAD_SIZE = 50 * 1024 * 10240;
    
    /**
     * When true, the active listener is not fired upon enqueue. When set to false,
     * the active listener will be fired if the queue is non-empty. 
     */
    private boolean paused;
    
    /**
     * The list of JavaScript statements to be sent to JavaScript.
     */
    private final LinkedList<JsMessage> queue = new LinkedList<JsMessage>();

    /**
     * The array of listeners that can be used to send messages to JS.
     */
    private final BridgeMode[] registeredListeners;    
    
    /**
     * When null, the bridge is disabled. This occurs during page transitions.
     * When disabled, all callbacks are dropped since they are assumed to be
     * relevant to the previous page.
     */
    private BridgeMode activeBridgeMode;

    private final CordovaInterface cordova;
    private final CordovaWebView webView;

    /**
     * Same as popAndEncode(), except encodes in a form that can be executed as JS.
     */
    private String popAndEncodeAsJs() {
        synchronized (this) {
            int length = queue.size();
            if (length == 0) {
                return null;
            }
            int totalPayloadLen = 0;
            int numMessagesToSend = 0;
            for (JsMessage message : queue) {
                int messageSize = message.calculateEncodedLength() + 50; // overestimate.
                if (numMessagesToSend > 0 && totalPayloadLen + messageSize > MAX_PAYLOAD_SIZE && MAX_PAYLOAD_SIZE > 0) {
                    break;
                }
                totalPayloadLen += messageSize;
                numMessagesToSend += 1;
            }
            boolean willSendAllMessages = numMessagesToSend == queue.size();
            StringBuilder sb = new StringBuilder(totalPayloadLen + (willSendAllMessages ? 0 : 100));
            // Wrap each statement in a try/finally so that if one throws it does 
            // not affect the next.
            for (int i = 0; i < numMessagesToSend; ++i) {
                JsMessage message = queue.removeFirst();
                if (willSendAllMessages && (i + 1 == numMessagesToSend)) {
                    message.encodeAsJsMessage(sb);
                } else {
                    sb.append("try{");
                    message.encodeAsJsMessage(sb);
                    sb.append("}finally{");
                }
            }
            if (!willSendAllMessages) {
                sb.append("window.setTimeout(function(){cordova.require('cordova/plugin/android/polling').pollOnce();},0);");
            }
            for (int i = willSendAllMessages ? 1 : 0; i < numMessagesToSend; ++i) {
                sb.append('}');
            }
            String ret = sb.toString();
            return ret;
        }
    }   

    private abstract class BridgeMode {
    }

    /** Uses JS polls for messages on a timer.. */
    private class PollingBridgeMode extends BridgeMode {
    }

    /** Uses webView.loadUrl("javascript:") to execute messages. */
    private class LoadUrlBridgeMode extends BridgeMode {
        final Runnable runnable = new Runnable() {
            public void run() {
                String js = popAndEncodeAsJs();
                if (js != null) {
                    webView.loadUrlNow("javascript:" + js);
                }
            }
        };
    }

    /** Uses online/offline events to tell the JS when to poll for messages. */
    private class OnlineEventsBridgeMode extends BridgeMode {
        private boolean online;
        private boolean ignoreNextFlush;

        final Runnable toggleNetworkRunnable = new Runnable() {
            public void run() {
                if (!queue.isEmpty()) {
                    ignoreNextFlush = false;
                    webView.setNetworkAvailable(online);
                }
            }
        };
        final Runnable resetNetworkRunnable = new Runnable() {
            public void run() {
                online = false;
                // If the following call triggers a notifyOfFlush, then ignore it.
                ignoreNextFlush = true;
                webView.setNetworkAvailable(true);
            }
        };
    }
    
    /**
     * Uses Java reflection to access an API that lets us eval JS.
     * Requires Android 3.2.4 or above. 
     */
    private class PrivateApiBridgeMode extends BridgeMode {
    	// Message added in commit:
    	// http://omapzoom.org/?p=platform/frameworks/base.git;a=commitdiff;h=9497c5f8c4bc7c47789e5ccde01179abc31ffeb2
    	// Which first appeared in 3.2.4ish.
    	private static final int EXECUTE_JS = 194;
    	
    	Method sendMessageMethod;
    	Object webViewCore;
    	boolean initFailed;
    }    
    private static class JsMessage {
        final String jsPayloadOrCallbackId;
        final PluginResult pluginResult;
        int calculateEncodedLength() {
            if (pluginResult == null) {
                return jsPayloadOrCallbackId.length() + 1;
            }
            int statusLen = String.valueOf(pluginResult.getStatus()).length();
            int ret = 2 + statusLen + 1 + jsPayloadOrCallbackId.length() + 1;
            return ret + calculateEncodedLengthHelper(pluginResult);
            }
    }
}
