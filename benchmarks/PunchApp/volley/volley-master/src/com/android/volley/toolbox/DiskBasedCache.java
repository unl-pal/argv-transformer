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

import org.sosy_lab.sv_benchmarks.Verifier;

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
        float HYSTERESIS_FACTOR = Verifier.nondetFloat();
		int mMaxCacheSizeInBytes = Verifier.nondetInt();
		int mTotalSize = Verifier.nondetInt();
		if ((mTotalSize + neededSpace) < mMaxCacheSizeInBytes) {
            return;
        }
        if (Verifier.nondetBoolean()) {
        }

        long before = mTotalSize;
        int prunedFiles = 0;
        long startTime = Verifier.nondetInt();

        while (Verifier.nondetBoolean()) {
            boolean deleted = Verifier.nondetBoolean();
            if (deleted) {
                mTotalSize -= Verifier.nondetInt();
            } else {
            }
            prunedFiles++;

            if ((mTotalSize + neededSpace) < mMaxCacheSizeInBytes * HYSTERESIS_FACTOR) {
                break;
            }
        }

        if (Verifier.nondetBoolean()) {
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
