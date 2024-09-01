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

package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A socket connection to a remote peer. A connection hosts streams which can
 * send and receive data.
 *
 * <p>Many methods in this API are <strong>synchronous:</strong> the call is
 * completed before the method returns. This is typical for Java but atypical
 * for SPDY. This is motivated by exception transparency: an IOException that
 * was triggered by a certain caller can be caught and handled by that caller.
 */
public final class SpdyConnection implements Closeable {

  // Internal state of this connection is guarded by 'this'. No blocking
  // operations may be performed while holding this lock!
  //
  // Socket writes are guarded by frameWriter.
  //
  // Socket reads are unguarded but are only made by the reader thread.
  //
  // Certain operations (like SYN_STREAM) need to synchronize on both the
  // frameWriter (to do blocking I/O) and this (to create streams). Such
  // operations must synchronize on 'this' last. This ensures that we never
  // wait for a blocking operation while holding 'this'.

  private static final ExecutorService executor = new ThreadPoolExecutor(0,
      Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
      Util.daemonThreadFactory("OkHttp SpdyConnection"));

  /** The protocol variant, like SPDY/3 or HTTP-draft-06/2.0. */
  final Variant variant;

  /** True if this peer initiated the connection. */
  final boolean client;

  /**
   * User code to run in response to an incoming stream. Callbacks must not be
   * run on the callback executor.
   */
  private final IncomingStreamHandler handler;
  private final FrameReader frameReader;
  private final FrameWriter frameWriter;

  private final Map<Integer, SpdyStream> streams = new HashMap<Integer, SpdyStream>();
  private final String hostName;
  private int lastGoodStreamId;
  private int nextStreamId;
  private boolean shutdown;
  private long idleStartTimeNs = System.nanoTime();

  /** Lazily-created map of in-flight pings awaiting a response. Guarded by this. */
  private Map<Integer, Ping> pings;
  private int nextPingId;

  /** Lazily-created settings for the peer. */
  Settings settings;

  public static class Builder {
    private String hostName;
    private InputStream in;
    private OutputStream out;
    private IncomingStreamHandler handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
    private Variant variant = Variant.SPDY3;
    private boolean client;
  }

  private class Reader implements Runnable, FrameReader.Handler {
  }
}
