/*
 * Copyright (C) 2012 Square, Inc.
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
package com.squareup.okhttp.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import javax.net.ssl.SSLSocket;

/**
 * Access to Platform-specific features necessary for SPDY and advanced TLS.
 *
 * <h3>SPDY</h3>
 * SPDY requires a TLS extension called NPN (Next Protocol Negotiation) that's
 * available in Android 4.1+ and OpenJDK 7+ (with the npn-boot extension). It
 * also requires a recent version of {@code DeflaterOutputStream} that is
 * public API in Java 7 and callable via reflection in Android 4.1+.
 */
public class Platform {
  private static final Platform PLATFORM = findPlatform();

  private Constructor<DeflaterOutputStream> deflaterConstructor;

  /** Android version 2.3 and newer support TLS session tickets and server name indication (SNI). */
  private static class Android23 extends Platform {
    protected final Class<?> openSslSocketClass;
    private final Method setUseSessionTickets;
    private final Method setHostname;
  }

  /** Android version 4.1 and newer support NPN. */
  private static class Android41 extends Android23 {
    private final Method setNpnProtocols;
    private final Method getNpnSelectedProtocol;
  }

  /** OpenJDK 7 plus {@code org.mortbay.jetty.npn/npn-boot} on the boot class path. */
  private static class JdkWithJettyNpnPlatform extends Platform {
    private final Method getMethod;
    private final Method putMethod;
    private final Class<?> clientProviderClass;
    private final Class<?> serverProviderClass;
  }

  /**
   * Handle the methods of NextProtoNego's ClientProvider and ServerProvider
   * without a compile-time dependency on those interfaces.
   */
  private static class JettyNpnProvider implements InvocationHandler {
    private final List<String> protocols;
    private boolean unsupported;
    private String selected;
  }
}
