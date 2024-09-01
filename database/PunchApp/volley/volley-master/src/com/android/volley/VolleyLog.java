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

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Logging helper class. */
public class VolleyLog {
    public static String TAG = "Volley";

    public static boolean DEBUG = Log.isLoggable(TAG, Log.VERBOSE);

    /**
     * A simple event log with records containing a name, thread ID, and timestamp.
     */
    static class MarkerLog {
        public static final boolean ENABLED = VolleyLog.DEBUG;

        /** Minimum duration from first marker to last in an marker log to warrant logging. */
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;
        }

        private final List<Marker> mMarkers = new ArrayList<Marker>();
        private boolean mFinished = false;
    }
}
