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

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Base64;

public class PluginResult {
    private final int status;
    private final int messageType;
    private boolean keepCallback = false;
    private String strMessage;
    private String encodedMessage;
    private List<PluginResult> multipartMessages;

    public static final int MESSAGE_TYPE_STRING = 1;
    public static final int MESSAGE_TYPE_JSON = 2;
    public static final int MESSAGE_TYPE_NUMBER = 3;
    public static final int MESSAGE_TYPE_BOOLEAN = 4;
    public static final int MESSAGE_TYPE_NULL = 5;
    public static final int MESSAGE_TYPE_ARRAYBUFFER = 6;
    // Use BINARYSTRING when your string may contain null characters.
    // This is required to work around a bug in the platform :(.
    public static final int MESSAGE_TYPE_BINARYSTRING = 7;
    public static final int MESSAGE_TYPE_MULTIPART = 8;

    public static String[] StatusMessages = new String[] {
        "No result",
        "OK",
        "Class not found",
        "Illegal access",
        "Instantiation error",
        "Malformed url",
        "IO error",
        "Invalid action",
        "JSON error",
        "Error"
    };

    public enum Status {
        NO_RESULT,
        OK,
        CLASS_NOT_FOUND_EXCEPTION,
        ILLEGAL_ACCESS_EXCEPTION,
        INSTANTIATION_EXCEPTION,
        MALFORMED_URL_EXCEPTION,
        IO_EXCEPTION,
        INVALID_ACTION,
        JSON_EXCEPTION,
        ERROR
    }
}
