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

import com.android.volley.Request.Priority;
import com.android.volley.mock.MockNetwork;
import com.android.volley.mock.MockRequest;
import com.android.volley.toolbox.NoCache;
import com.android.volley.utils.CacheTestUtils;
import com.android.volley.utils.ImmediateResponseDelivery;

import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@LargeTest
public class RequestQueueTest extends InstrumentationTestCase {
    private ResponseDelivery mDelivery;

    private class OrderCheckingNetwork implements Network {
        private Priority mLastPriority = Priority.IMMEDIATE;
        private int mLastSequence = -1;
        private Semaphore mSemaphore;
    }

    private class DelayedRequest extends Request<Object> {
        private final long mDelayMillis;
        private final AtomicInteger mParsedCount;
        private final AtomicInteger mDeliveredCount;
    }

}
