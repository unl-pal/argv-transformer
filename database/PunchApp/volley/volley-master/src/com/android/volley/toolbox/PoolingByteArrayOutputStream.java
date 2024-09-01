/*
 * Copyright (C) 2012 The Android Open Source Project
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A variation of {@link java.io.ByteArrayOutputStream} that uses a pool of byte[] buffers instead
 * of always allocating them fresh, saving on heap churn.
 */
public class PoolingByteArrayOutputStream extends ByteArrayOutputStream {
    /**
     * If the {@link #PoolingByteArrayOutputStream(ByteArrayPool)} constructor is called, this is
     * the default size to which the underlying byte array is initialized.
     */
    private static final int DEFAULT_SIZE = 256;

    private final ByteArrayPool mPool;
}
