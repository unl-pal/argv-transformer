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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * This class is the main Android activity that represents the Cordova
 * application. It should be extended by the user to load the specific
 * html file that contains the application.
 *
 * As an example:
 * 
 * <pre>
 *     package org.apache.cordova.examples;
 *
 *     import android.os.Bundle;
 *     import org.apache.cordova.*;
 *
 *     public class Example extends CordovaActivity {
 *       &#64;Override
 *       public void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         super.init();
 *         // Load your application
 *         loadUrl(launchUrl);
 *       }
 *     }
 * </pre>
 * 
 * Cordova xml configuration: Cordova uses a configuration file at 
 * res/xml/config.xml to specify its settings. See "The config.xml File"
 * guide in cordova-docs at http://cordova.apache.org/docs for the documentation
 * for the configuration. The use of the set*Property() methods is
 * deprecated in favor of the config.xml file.
 *
 */
public class CordovaActivity extends Activity implements CordovaInterface {
    public static String TAG = "CordovaActivity";

    // The webview for our app
    protected CordovaWebView appView;

    @Deprecated // unused.
    protected CordovaWebViewClient webViewClient;

    @Deprecated // Will be removed. Use findViewById() to retrieve views.
    protected LinearLayout root;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private static int ACTIVITY_STARTING = 0;
    private static int ACTIVITY_RUNNING = 1;
    private static int ACTIVITY_EXITING = 2;
    private int activityState = 0;  // 0=starting, 1=running (after 1st resume), 2=shutting down

    // Plugin to call when activity result is received
    protected int activityResultRequestCode;
    protected CordovaPlugin activityResultCallback;
    protected boolean activityResultKeepRunning;

    /*
     * The variables below are used to cache some of the activity properties.
     */

    // Draw a splash screen using an image located in the drawable resource directory.
    @Deprecated // Use "SplashScreen" preference instead.
    protected int splashscreen = 0;
    @Deprecated // Use "SplashScreenDelay" preference instead.
    protected int splashscreenTime = -1;

    // LoadUrl timeout value in msec (default of 20 sec)
    protected int loadUrlTimeoutValue = 20000;

    // Keep app running when pause is received. (default = true)
    // If true, then the JavaScript and native code continue to run in the background
    // when another application (activity) is started.
    protected boolean keepRunning = true;

    private String initCallbackClass;

    // Read from config.xml:
    protected CordovaPreferences preferences;
    protected Whitelist internalWhitelist;
    protected Whitelist externalWhitelist;
    protected String launchUrl;
    protected ArrayList<PluginEntry> pluginEntries;
}
