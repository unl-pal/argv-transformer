/*
 * Copyright (C) 2012 Square, Inc.
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

import com.squareup.okhttp.OkAuthenticator;
import com.squareup.okhttp.OkAuthenticator.Challenge;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.squareup.okhttp.OkAuthenticator.Credential;
import static java.net.HttpURLConnection.HTTP_PROXY_AUTH;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/** Handles HTTP authentication headers from origin and proxy servers. */
public final class HttpAuthenticator {
  /** Uses the global authenticator to get the password. */
  public static final OkAuthenticator SYSTEM_DEFAULT = new OkAuthenticator() {
    @Override public Credential authenticate(
        Proxy proxy, URL url, List<Challenge> challenges) throws IOException {
      for (Challenge challenge : challenges) {
        if (!"Basic".equalsIgnoreCase(challenge.getScheme())) {
          continue;
        }

        PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(url.getHost(),
            getConnectToInetAddress(proxy, url), url.getPort(), url.getProtocol(),
            challenge.getRealm(), challenge.getScheme(), url, Authenticator.RequestorType.SERVER);
        if (auth != null) {
          return Credential.basic(auth.getUserName(), new String(auth.getPassword()));
        }
      }
      return null;
    }

    @Override public Credential authenticateProxy(
        Proxy proxy, URL url, List<Challenge> challenges) throws IOException {
      for (Challenge challenge : challenges) {
        if (!"Basic".equalsIgnoreCase(challenge.getScheme())) {
          continue;
        }

        InetSocketAddress proxyAddress = (InetSocketAddress) proxy.address();
        PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(
            proxyAddress.getHostName(), getConnectToInetAddress(proxy, url), proxyAddress.getPort(),
            url.getProtocol(), challenge.getRealm(), challenge.getScheme(), url,
            Authenticator.RequestorType.PROXY);
        if (auth != null) {
          return Credential.basic(auth.getUserName(), new String(auth.getPassword()));
        }
      }
      return null;
    }

    private InetAddress getConnectToInetAddress(Proxy proxy, URL url) throws IOException {
      return (proxy != null && proxy.type() != Proxy.Type.DIRECT)
          ? ((InetSocketAddress) proxy.address()).getAddress()
          : InetAddress.getByName(url.getHost());
    }
  };
}
