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

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.SSLHandshakeException;

/**
 * A blacklist of failed routes to avoid when creating a new connection to a
 * target address. This is used so that OkHttp can learn from its mistakes: if
 * there was a failure attempting to connect to a specific IP address, proxy
 * server or TLS mode, that failure is remembered and alternate routes are
 * preferred.
 */
public final class RouteDatabase {
  private final Set<Route> failedRoutes = new LinkedHashSet<Route>();
}
