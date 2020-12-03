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

/** filtered and transformed by PAClab */
package com.android.volley.toolbox;

import gov.nasa.jpf.symbc.Debug;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cache implementation that caches files directly onto the hard disk in the specified
 * directory. The default disk usage size is 5MB, but is configurable.
 */
public class DiskBasedCache {

    /**
     * Prunes the cache to fit the amount of bytes specified.
     * @param neededSpace The amount of bytes we are trying to fit into the cache.
     */
    /** PACLab: suitable */
	 private void pruneIfNeeded(int neededSpace) {
        float HYSTERESIS_FACTOR = (float) Debug.makeSymbolicReal("x7");
		int mMaxCacheSizeInBytes = Debug.makeSymbolicInteger("x1");
		int mTotalSize = Debug.makeSymbolicInteger("x0");
		if ((mTotalSize + neededSpace) < mMaxCacheSizeInBytes) {
            return;
        }
        if (Debug.makeSymbolicBoolean("x2")) {
        }

        long before = mTotalSize;
        int prunedFiles = 0;
        long startTime = Debug.makeSymbolicInteger("x3");

        while (Debug.makeSymbolicBoolean("x4")) {
            boolean deleted = Debug.makeSymbolicBoolean("x5");
            if (deleted) {
                mTotalSize -= Debug.makeSymbolicInteger("x6");
            } else {
            }
            prunedFiles++;

            if ((mTotalSize + neededSpace) < mMaxCacheSizeInBytes * HYSTERESIS_FACTOR) {
                break;
            }
        }

        if (Debug.makeSymbolicBoolean("x8")) {
        }
    }

    /**
     * Handles holding onto the cache headers for an entry.
     */
    // Visible for testing.
    static class CacheHeader {

    }

    private static class CountingInputStream {
    }


}
