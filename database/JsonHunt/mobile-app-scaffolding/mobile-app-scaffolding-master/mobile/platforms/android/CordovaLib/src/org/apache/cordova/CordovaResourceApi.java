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

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import com.squareup.okhttp.OkHttpClient;

import org.apache.http.util.EncodingUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Locale;

/**
 * What this class provides:
 * 1. Helpers for reading & writing to URLs.
 *   - E.g. handles assets, resources, content providers, files, data URIs, http[s]
 *   - E.g. Can be used to query for mime-type & content length.
 *
 * 2. To allow plugins to redirect URLs (via remapUrl).
 *   - All plugins should call remapUrl() on URLs they receive from JS *before*
 *     passing the URL onto other utility functions in this class.
 *   - For an example usage of this, refer to the org.apache.cordova.file plugin.
 *
 * 3. It exposes a way to use the OkHttp library that ships with Cordova.
 *   - Through createHttpConnection().
 *
 * Future Work:
 *   - Consider using a Cursor to query content URLs for their size (like the file plugin does).
 *   - Allow plugins to remapUri to "cdv-plugin://plugin-name/$ID", which CordovaResourceApi
 *     would then delegate to pluginManager.getPlugin(plugin-name).openForRead($ID)
 *     - Currently, plugins *can* do this by remapping to a data: URL, but it's inefficient
 *       for large payloads.
 */
public class CordovaResourceApi {
    @SuppressWarnings("unused")
    private static final String LOG_TAG = "CordovaResourceApi";

    public static final int URI_TYPE_FILE = 0;
    public static final int URI_TYPE_ASSET = 1;
    public static final int URI_TYPE_CONTENT = 2;
    public static final int URI_TYPE_RESOURCE = 3;
    public static final int URI_TYPE_DATA = 4;
    public static final int URI_TYPE_HTTP = 5;
    public static final int URI_TYPE_HTTPS = 6;
    public static final int URI_TYPE_UNKNOWN = -1;
    
    private static final String[] LOCAL_FILE_PROJECTION = { "_data" };
    
    // Creating this is light-weight.
    private static OkHttpClient httpClient = new OkHttpClient();
    
    static Thread jsThread;

    private final AssetManager assetManager;
    private final ContentResolver contentResolver;
    private final PluginManager pluginManager;
    private boolean threadCheckingEnabled = true;


    public static final class OpenForReadResult {
        public final Uri uri;
        public final InputStream inputStream;
        public final String mimeType;
        public final long length;
        public final AssetFileDescriptor assetFd;
    }
}
