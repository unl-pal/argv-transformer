/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.toolbox;

import android.test.suitebuilder.annotation.SmallTest;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

@SmallTest
public class HttpHeaderParserTest extends TestCase {

    private static long ONE_MINUTE_MILLIS = 1000L * 60;
    private static long ONE_HOUR_MILLIS = 1000L * 60 * 60;

    private NetworkResponse response;
    private Map<String, String> headers;
}
