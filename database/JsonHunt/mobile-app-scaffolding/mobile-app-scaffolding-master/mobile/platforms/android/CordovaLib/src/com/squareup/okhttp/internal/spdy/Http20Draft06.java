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
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.Util;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Read and write http/2 v06 frames.
 * http://tools.ietf.org/html/draft-ietf-httpbis-http2-06
 */
final class Http20Draft06 implements Variant {
  private static final byte[] CONNECTION_HEADER;
  static {
    try {
      CONNECTION_HEADER = "PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n".getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new AssertionError();
    }
  }

  static final int TYPE_DATA = 0x0;
  static final int TYPE_HEADERS = 0x1;
  static final int TYPE_PRIORITY = 0x2;
  static final int TYPE_RST_STREAM = 0x3;
  static final int TYPE_SETTINGS = 0x4;
  static final int TYPE_PUSH_PROMISE = 0x5;
  static final int TYPE_PING = 0x6;
  static final int TYPE_GOAWAY = 0x7;
  static final int TYPE_WINDOW_UPDATE = 0x9;
  static final int TYPE_CONTINUATION = 0xa;

  static final int FLAG_END_STREAM = 0x1;
  /** Used for headers, push-promise and continuation. */
  static final int FLAG_END_HEADERS = 0x4;
  static final int FLAG_PRIORITY = 0x8;
  static final int FLAG_PONG = 0x1;
  static final int FLAG_END_FLOW_CONTROL = 0x1;

  static final class Reader implements FrameReader {
    private final DataInputStream in;
    private final boolean client;
    private final Hpack.Reader hpackReader;
  }

  static final class Writer implements FrameWriter {
    private final DataOutputStream out;
    private final boolean client;
    private final ByteArrayOutputStream hpackBuffer;
    private final Hpack.Writer hpackWriter;
  }
}
