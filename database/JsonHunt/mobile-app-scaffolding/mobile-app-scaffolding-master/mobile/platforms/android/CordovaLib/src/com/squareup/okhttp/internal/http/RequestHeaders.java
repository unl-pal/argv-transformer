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

package com.squareup.okhttp.internal.http;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** Parsed HTTP request headers. */
public final class RequestHeaders {
  private final URI uri;
  private final RawHeaders headers;

  /** Don't use a cache to satisfy this request. */
  private boolean noCache;
  private int maxAgeSeconds = -1;
  private int maxStaleSeconds = -1;
  private int minFreshSeconds = -1;

  /**
   * This field's name "only-if-cached" is misleading. It actually means "do
   * not use the network". It is set by a client who only wants to make a
   * request if it can be fully satisfied by the cache. Cached responses that
   * would require validation (ie. conditional gets) are not permitted if this
   * header is set.
   */
  private boolean onlyIfCached;

  /**
   * True if the request contains an authorization field. Although this isn't
   * necessarily a shared cache, it follows the spec's strict requirements for
   * shared caches.
   */
  private boolean hasAuthorization;

  private long contentLength = -1;
  private String transferEncoding;
  private String userAgent;
  private String host;
  private String connection;
  private String acceptEncoding;
  private String contentType;
  private String ifModifiedSince;
  private String ifNoneMatch;
  private String proxyAuthorization;
}
