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

package com.android.volley.mock;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class TestRequest {
    private static final String TEST_URL = "http://foo.com";

    /** Base Request class for testing allowing both the deprecated and new constructor. */
    private static class Base extends Request<byte[]> {
    }

    /** Test example of a GET request in the deprecated style. */
    public static class DeprecatedGet extends Base {
    }

    /** Test example of a POST request in the deprecated style. */
    public static class DeprecatedPost extends Base {
        private Map<String, String> mPostParams;
    }

    /** Test example of a GET request in the new style. */
    public static class Get extends Base {
    }

    /**
     * Test example of a POST request in the new style.  In the new style, it is possible
     * to have a POST with no body.
     */
    public static class Post extends Base {
    }

    /** Test example of a POST request in the new style with a body. */
    public static class PostWithBody extends Post {
        private Map<String, String> mParams;
    }

    /**
     * Test example of a PUT request in the new style.  In the new style, it is possible to have a
     * PUT with no body.
     */
    public static class Put extends Base {
    }

    /** Test example of a PUT request in the new style with a body. */
    public static class PutWithBody extends Put {
        private Map<String, String> mParams = new HashMap<String, String>();
    }

    /** Test example of a DELETE request in the new style. */
    public static class Delete extends Base {
    }

    /** Test example of a HEAD request in the new style. */
    public static class Head extends Base {
    }

    /** Test example of a OPTIONS request in the new style. */
    public static class Options extends Base {
    }

    /** Test example of a TRACE request in the new style. */
    public static class Trace extends Base {
    }

    /** Test example of a PATCH request in the new style. */
    public static class Patch extends Base {
    }

    /** Test example of a PATCH request in the new style with a body. */
    public static class PatchWithBody extends Patch {
        private Map<String, String> mParams = new HashMap<String, String>();
    }
}
