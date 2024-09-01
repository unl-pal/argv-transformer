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
package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.RawHeaders;

import static com.squareup.okhttp.internal.Util.getDefaultPort;

/**
 * Routing and authentication information sent to an HTTP proxy to create a
 * HTTPS to an origin server. Everything in the tunnel request is sent
 * unencrypted to the proxy server.
 *
 * <p>See <a href="http://www.ietf.org/rfc/rfc2817.txt">RFC 2817, Section
 * 5.2</a>.
 */
public final class TunnelRequest {
  final String host;
  final int port;
  final String userAgent;
  final String proxyAuthorization;
}
