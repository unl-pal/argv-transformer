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

import com.squareup.okhttp.ResponseSource;
import com.squareup.okhttp.internal.Platform;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static com.squareup.okhttp.internal.Util.equal;

/** Parsed HTTP response headers. */
public final class ResponseHeaders {

  /** HTTP header name for the local time when the request was sent. */
  private static final String SENT_MILLIS = Platform.get().getPrefix() + "-Sent-Millis";

  /** HTTP header name for the local time when the response was received. */
  private static final String RECEIVED_MILLIS = Platform.get().getPrefix() + "-Received-Millis";

  /** HTTP synthetic header with the response source. */
  static final String RESPONSE_SOURCE = Platform.get().getPrefix() + "-Response-Source";

  /** HTTP synthetic header with the selected transport (spdy/3, http/1.1, etc). */
  static final String SELECTED_TRANSPORT = Platform.get().getPrefix() + "-Selected-Transport";

  private final URI uri;
  private final RawHeaders headers;

  /** The server's time when this response was served, if known. */
  private Date servedDate;

  /** The last modified date of the response, if known. */
  private Date lastModified;

  /**
   * The expiration date of the response, if known. If both this field and the
   * max age are set, the max age is preferred.
   */
  private Date expires;

  /**
   * Extension header set by HttpURLConnectionImpl specifying the timestamp
   * when the HTTP request was first initiated.
   */
  private long sentRequestMillis;

  /**
   * Extension header set by HttpURLConnectionImpl specifying the timestamp
   * when the HTTP response was first received.
   */
  private long receivedResponseMillis;

  /**
   * In the response, this field's name "no-cache" is misleading. It doesn't
   * prevent us from caching the response; it only means we have to validate
   * the response with the origin server before returning it. We can do this
   * with a conditional get.
   */
  private boolean noCache;

  /** If true, this response should not be cached. */
  private boolean noStore;

  /**
   * The duration past the response's served date that it can be served
   * without validation.
   */
  private int maxAgeSeconds = -1;

  /**
   * The "s-maxage" directive is the max age for shared caches. Not to be
   * confused with "max-age" for non-shared caches, As in Firefox and Chrome,
   * this directive is not honored by this cache.
   */
  private int sMaxAgeSeconds = -1;

  /**
   * This request header field's name "only-if-cached" is misleading. It
   * actually means "do not use the network". It is set by a client who only
   * wants to make a request if it can be fully satisfied by the cache.
   * Cached responses that would require validation (ie. conditional gets) are
   * not permitted if this header is set.
   */
  private boolean isPublic;
  private boolean mustRevalidate;
  private String etag;
  private int ageSeconds = -1;

  /** Case-insensitive set of field names. */
  private Set<String> varyFields = Collections.emptySet();

  private String contentEncoding;
  private String transferEncoding;
  private long contentLength = -1;
  private String connection;
  private String contentType;

  /**
   * Returns the number of milliseconds that the response was fresh for,
   * starting from the served date.
   */
  private long computeFreshnessLifetime() {
    if (maxAgeSeconds != -1) {
      return TimeUnit.SECONDS.toMillis(maxAgeSeconds);
    } else if (expires != null) {
      long servedMillis = servedDate != null ? servedDate.getTime() : receivedResponseMillis;
      long delta = expires.getTime() - servedMillis;
      return delta > 0 ? delta : 0;
    } else if (lastModified != null && uri.getRawQuery() == null) {
      // As recommended by the HTTP RFC and implemented in Firefox, the
      // max age of a document should be defaulted to 10% of the
      // document's age at the time it was served. Default expiration
      // dates aren't used for URIs containing a query.
      long servedMillis = servedDate != null ? servedDate.getTime() : sentRequestMillis;
      long delta = servedMillis - lastModified.getTime();
      return delta > 0 ? (delta / 10) : 0;
    }
    return 0;
  }
}
