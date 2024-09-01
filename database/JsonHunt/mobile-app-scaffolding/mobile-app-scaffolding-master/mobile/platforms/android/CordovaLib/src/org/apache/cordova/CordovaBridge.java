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

import java.security.SecureRandom;

import org.apache.cordova.PluginManager;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

/**
 * Contains APIs that the JS can call. All functions in here should also have
 * an equivalent entry in CordovaChromeClient.java, and be added to
 * cordova-js/lib/android/plugin/android/promptbasednativeapi.js
 */
public class CordovaBridge {
    private static final String LOG_TAG = "CordovaBridge";
    private PluginManager pluginManager;
    private NativeToJsMessageQueue jsMessageQueue;
    private volatile int expectedBridgeSecret = -1; // written by UI thread, read by JS thread.
    private String loadedUrl;
    private String appContentUrlPrefix;
}
