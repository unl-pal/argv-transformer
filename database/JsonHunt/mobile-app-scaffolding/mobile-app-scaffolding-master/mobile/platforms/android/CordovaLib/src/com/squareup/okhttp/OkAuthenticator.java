/*
 * Copyright (C) 2013 Square, Inc.
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
package com.squareup.okhttp;

import com.squareup.okhttp.internal.Base64;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

/**
 * Responds to authentication challenges from the remote web or proxy server by
 * returning credentials.
 */
public interface OkAuthenticator {
  /** An RFC 2617 challenge. */
  public final class Challenge {
    private final String scheme;
    private final String realm;
  }

  /** An RFC 2617 credential. */
  public final class Credential {
    private final String headerValue;
  }
}
