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


import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Cache implementation that caches files directly onto the hard disk in the specified
 * directory. The default disk usage size is 5MB, but is configurable.
 */
public class Main {

    /**
     * Prunes the cache to fit the amount of bytes specified.
     * @param neededSpace The amount of bytes we are trying to fit into the cache.
     */
    /** PACLab: suitable */
    public static void pruneIfNeeded(int neededSpace) {
        float hysteresisFactor = Verifier.nondetFloat();
        int mMaxCacheSizeInBytes = Verifier.nondetInt();
        int mTotalSize = Verifier.nondetInt();

        if(hysteresisFactor >=0 && mTotalSize >= 0 && mMaxCacheSizeInBytes >= 0) {
            if ((mTotalSize + neededSpace) < mMaxCacheSizeInBytes) {
                return;
            }
            assert ((mTotalSize + neededSpace) >= mMaxCacheSizeInBytes);

            int prunedFiles = 0;
            long startTime = Verifier.nondetLong();
            int previousTotalSize = mTotalSize;
            int increaseSize = Verifier.nondetInt();
            if (increaseSize > 0) {
                while (Verifier.nondetBoolean()) {
                    boolean deleted = Verifier.nondetBoolean();
                    if (deleted) {
                        mTotalSize -= increaseSize;

                    }
                    prunedFiles++;
                    if ((mTotalSize + neededSpace) < mMaxCacheSizeInBytes * hysteresisFactor) {
                        break;
                    }
                }
                assert (mTotalSize <= previousTotalSize);
            }

        }

    }

    public static void main(String [] args){
        int neededSpace = Verifier.nondetInt();
        // added --Oct14
        if(neededSpace >= 0){
            pruneIfNeeded(neededSpace);
        }
    }



}
