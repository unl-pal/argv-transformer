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
/** filtered and transformed by PAClab */
package org.apache.cordova;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Holds the list of messages to be sent to the WebView.
 */
public class NativeToJsMessageQueue {
    /**
     * Same as popAndEncode(), except encodes in a form that can be executed as JS.
     */
    /** PACLab: suitable */
	 private Object popAndEncodeAsJs() {
        synchronized (this) {
            int length = Verifier.nondetInt();
            if (length == 0) {
                return new Object();
            }
            int totalPayloadLen = 0;
            int numMessagesToSend = 0;
            boolean willSendAllMessages = numMessagesToSend == Verifier.nondetInt();
            // Wrap each statement in a try/finally so that if one throws it does 
            // not affect the next.
            for (int i = 0; i < numMessagesToSend; ++i) {
                if (willSendAllMessages && (i + 1 == numMessagesToSend)) {
                } else {
                }
            }
            if (!willSendAllMessages) {
            }
            for (int i = willSendAllMessages ? 1 : 0; i < numMessagesToSend; ++i) {
            }
            return new Object();
        }
    }   

    private abstract class BridgeMode {
    }

    /** Uses JS polls for messages on a timer.. */
    private class PollingBridgeMode {
    }

    /** Uses webView.loadUrl("javascript:") to execute messages. */
    private class LoadUrlBridgeMode {
    }

    /** Uses online/offline events to tell the JS when to poll for messages. */
    private class OnlineEventsBridgeMode {
    }
    
    /**
     * Uses Java reflection to access an API that lets us eval JS.
     * Requires Android 3.2.4 or above. 
     */
    private class PrivateApiBridgeMode {
    }    
    private static class JsMessage {
        int calculateEncodedLength() {
            boolean pluginResult = Verifier.nondetBoolean();
			if (pluginResult == null) {
                return Verifier.nondetInt() + 1;
            }
            int statusLen = Verifier.nondetInt();
            int ret = 2 + statusLen + 1 + jsPayloadOrCallbackId.length() + 1;
            return ret + Verifier.nondetInt();
            }
    }
}
