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

import com.android.volley.mock.MockRequest;
import com.android.volley.utils.CacheTestUtils;
import com.android.volley.utils.ImmediateResponseDelivery;

import junit.framework.TestCase;

@MediumTest
public class ResponseDeliveryTest extends TestCase {

    private ExecutorDelivery mDelivery;
    private MockRequest mRequest;
    private Response<byte[]> mSuccessResponse;
}
