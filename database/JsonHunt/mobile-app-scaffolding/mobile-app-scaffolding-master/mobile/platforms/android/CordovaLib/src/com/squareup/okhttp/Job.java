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

import com.squareup.okhttp.internal.http.HttpAuthenticator;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.HttpsEngine;
import com.squareup.okhttp.internal.http.Policy;
import com.squareup.okhttp.internal.http.RawHeaders;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;

import static com.squareup.okhttp.internal.Util.getEffectivePort;
import static com.squareup.okhttp.internal.http.HttpURLConnectionImpl.HTTP_MOVED_PERM;
import static com.squareup.okhttp.internal.http.HttpURLConnectionImpl.HTTP_MOVED_TEMP;
import static com.squareup.okhttp.internal.http.HttpURLConnectionImpl.HTTP_MULT_CHOICE;
import static com.squareup.okhttp.internal.http.HttpURLConnectionImpl.HTTP_PROXY_AUTH;
import static com.squareup.okhttp.internal.http.HttpURLConnectionImpl.HTTP_SEE_OTHER;
import static com.squareup.okhttp.internal.http.HttpURLConnectionImpl.HTTP_TEMP_REDIRECT;
import static com.squareup.okhttp.internal.http.HttpURLConnectionImpl.HTTP_UNAUTHORIZED;

final class Job implements Runnable, Policy {
  private final Dispatcher dispatcher;
  private final OkHttpClient client;
  private final Response.Receiver responseReceiver;

  /** The request; possibly a consequence of redirects or auth headers. */
  private Request request;
}
