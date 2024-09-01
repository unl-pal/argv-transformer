/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkResponseCache;
import com.squareup.okhttp.ResponseSource;
import com.squareup.okhttp.TunnelRequest;
import com.squareup.okhttp.internal.Dns;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import static com.squareup.okhttp.internal.Util.EMPTY_BYTE_ARRAY;
import static com.squareup.okhttp.internal.Util.getDefaultPort;
import static com.squareup.okhttp.internal.Util.getEffectivePort;

/**
 * Handles a single HTTP request/response pair. Each HTTP engine follows this
 * lifecycle:
 * <ol>
 * <li>It is created.
 * <li>The HTTP request message is sent with sendRequest(). Once the request
 * is sent it is an error to modify the request headers. After
 * sendRequest() has been called the request body can be written to if
 * it exists.
 * <li>The HTTP response message is read with readResponse(). After the
 * response has been read the response headers and body can be read.
 * All responses have a response body input stream, though in some
 * instances this stream is empty.
 * </ol>
 *
 * <p>The request and response may be served by the HTTP response cache, by the
 * network, or by both in the event of a conditional GET.
 *
 * <p>This class may hold a socket connection that needs to be released or
 * recycled. By default, this socket connection is held when the last byte of
 * the response is consumed. To release the connection when it is no longer
 * required, use {@link #automaticallyReleaseConnectionToPool()}.
 */
public class HttpEngine {
  private static final CacheResponse GATEWAY_TIMEOUT_RESPONSE = new CacheResponse() {
    @Override public Map<String, List<String>> getHeaders() throws IOException {
      Map<String, List<String>> result = new HashMap<String, List<String>>();
      result.put(null, Collections.singletonList("HTTP/1.1 504 Gateway Timeout"));
      return result;
    }
    @Override public InputStream getBody() throws IOException {
      return new ByteArrayInputStream(EMPTY_BYTE_ARRAY);
    }
  };
  public static final int HTTP_CONTINUE = 100;

  protected final Policy policy;
  protected final OkHttpClient client;

  protected final String method;

  private ResponseSource responseSource;

  protected Connection connection;
  protected RouteSelector routeSelector;
  private OutputStream requestBodyOut;

  private Transport transport;

  private InputStream responseTransferIn;
  private InputStream responseBodyIn;

  private CacheResponse cacheResponse;
  private CacheRequest cacheRequest;

  /** The time when the request headers were written, or -1 if they haven't been written yet. */
  long sentRequestMillis = -1;

  /** Whether the connection has been established. */
  boolean connected;

  /**
   * True if this client added an "Accept-Encoding: gzip" header field and is
   * therefore responsible for also decompressing the transfer stream.
   */
  private boolean transparentGzip;

  final URI uri;

  final RequestHeaders requestHeaders;

  /** Null until a response is received from the network or the cache. */
  ResponseHeaders responseHeaders;

  // The cache response currently being validated on a conditional get. Null
  // if the cached response doesn't exist or doesn't need validation. If the
  // conditional get succeeds, these will be used for the response headers and
  // body. If it fails, these be closed and set to null.
  private ResponseHeaders cachedResponseHeaders;
  private InputStream cachedResponseBody;

  /**
   * True if the socket connection should be released to the connection pool
   * when the response has been fully read.
   */
  private boolean automaticallyReleaseConnectionToPool;

  /** True if the socket connection is no longer needed by this engine. */
  private boolean connectionReleased;
}
