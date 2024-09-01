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

package com.android.volley;

import android.test.suitebuilder.annotation.MediumTest;

import com.android.volley.mock.MockCache;
import com.android.volley.mock.MockNetwork;
import com.android.volley.mock.MockRequest;
import com.android.volley.mock.MockResponseDelivery;
import com.android.volley.mock.WaitableQueue;

import java.util.Arrays;

import junit.framework.TestCase;

@MediumTest
public class NetworkDispatcherTest extends TestCase {
    private NetworkDispatcher mDispatcher;
    private MockResponseDelivery mDelivery;
    private WaitableQueue mNetworkQueue;
    private MockNetwork mNetwork;
    private MockCache mCache;
    private MockRequest mRequest;

    private static final byte[] CANNED_DATA = "Ceci n'est pas une vraie reponse".getBytes();
    private static final long TIMEOUT_MILLIS = 5000;
}
