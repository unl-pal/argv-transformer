/*
 * Copyright (C) 2012 Square, Inc.
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

/** filtered by PAClab */
 final class Settings {
  /**
   * From the spdy/3 spec, the default initial window size for all streams is
   * 64 KiB. (Chrome 25 uses 10 MiB).
   */
  static final int DEFAULT_INITIAL_WINDOW_SIZE = 64 * 1024;

  /** Peer request to clear durable settings. */
  static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 0x1;

  /** Sent by servers only. The peer requests this setting persisted for future connections. */
  static final int PERSIST_VALUE = 0x1;
  /** Sent by clients only. The client is reminding the server of a persisted value. */
  static final int PERSISTED = 0x2;

  /** Sender's estimate of max incoming kbps. */
  static final int UPLOAD_BANDWIDTH = 1;
  /** Sender's estimate of max outgoing kbps. */
  static final int DOWNLOAD_BANDWIDTH = 2;
  /** Sender's estimate of milliseconds between sending a request and receiving a response. */
  static final int ROUND_TRIP_TIME = 3;
  /** Sender's maximum number of concurrent streams. */
  static final int MAX_CONCURRENT_STREAMS = 4;
  /** Current CWND in Packets. */
  static final int CURRENT_CWND = 5;
  /** Retransmission rate. Percentage */
  static final int DOWNLOAD_RETRANS_RATE = 6;
  /** Window size in bytes. */
  static final int INITIAL_WINDOW_SIZE = 7;
  /** Window size in bytes. */
  static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
  /** Flow control options. */
  static final int FLOW_CONTROL_OPTIONS = 9;

  /** Total number of settings. */
  static final int COUNT = 10;

  /** If set, flow control is disabled for streams directed to the sender of these settings. */
  static final int FLOW_CONTROL_OPTIONS_DISABLED = 0x1;

  /** Bitfield of which flags that values. */
  private int set;

  /** Bitfield of flags that have {@link #PERSIST_VALUE}. */
  private int persistValue;

  /** Bitfield of flags that have {@link #PERSISTED}. */
  private int persisted;

  /** Flag values. */
  private final int[] values = new int[COUNT];

  void set(int id, int idFlags, int value) {
    if (id >= values.length) {
      return; // Discard unknown settings.
    }

    int bit = 1 << id;
    set |= bit;
    if ((idFlags & PERSIST_VALUE) != 0) {
      persistValue |= bit;
    } else {
      persistValue &= ~bit;
    }
    if ((idFlags & PERSISTED) != 0) {
      persisted |= bit;
    } else {
      persisted &= ~bit;
    }

    values[id] = value;
  }
}
