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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/**
 * Read and write HPACK v03.
 * http://tools.ietf.org/html/draft-ietf-httpbis-header-compression-03
 */
final class Hpack {

  static class HeaderEntry {
    private final String name;
    private final String value;
  }

  static final int PREFIX_5_BITS = 0x1f;
  static final int PREFIX_6_BITS = 0x3f;
  static final int PREFIX_7_BITS = 0x7f;
  static final int PREFIX_8_BITS = 0xff;

  static final List<HeaderEntry> INITIAL_CLIENT_TO_SERVER_HEADER_TABLE = Arrays.asList(
      new HeaderEntry(":scheme", "http"),
      new HeaderEntry(":scheme", "https"),
      new HeaderEntry(":host", ""),
      new HeaderEntry(":path", "/"),
      new HeaderEntry(":method", "GET"),
      new HeaderEntry("accept", ""),
      new HeaderEntry("accept-charset", ""),
      new HeaderEntry("accept-encoding", ""),
      new HeaderEntry("accept-language", ""),
      new HeaderEntry("cookie", ""),
      new HeaderEntry("if-modified-since", ""),
      new HeaderEntry("user-agent", ""),
      new HeaderEntry("referer", ""),
      new HeaderEntry("authorization", ""),
      new HeaderEntry("allow", ""),
      new HeaderEntry("cache-control", ""),
      new HeaderEntry("connection", ""),
      new HeaderEntry("content-length", ""),
      new HeaderEntry("content-type", ""),
      new HeaderEntry("date", ""),
      new HeaderEntry("expect", ""),
      new HeaderEntry("from", ""),
      new HeaderEntry("if-match", ""),
      new HeaderEntry("if-none-match", ""),
      new HeaderEntry("if-range", ""),
      new HeaderEntry("if-unmodified-since", ""),
      new HeaderEntry("max-forwards", ""),
      new HeaderEntry("proxy-authorization", ""),
      new HeaderEntry("range", ""),
      new HeaderEntry("via", "")
  );

  static final List<HeaderEntry> INITIAL_SERVER_TO_CLIENT_HEADER_TABLE = Arrays.asList(
      new HeaderEntry(":status", "200"),
      new HeaderEntry("age", ""),
      new HeaderEntry("cache-control", ""),
      new HeaderEntry("content-length", ""),
      new HeaderEntry("content-type", ""),
      new HeaderEntry("date", ""),
      new HeaderEntry("etag", ""),
      new HeaderEntry("expires", ""),
      new HeaderEntry("last-modified", ""),
      new HeaderEntry("server", ""),
      new HeaderEntry("set-cookie", ""),
      new HeaderEntry("vary", ""),
      new HeaderEntry("via", ""),
      new HeaderEntry("access-control-allow-origin", ""),
      new HeaderEntry("accept-ranges", ""),
      new HeaderEntry("allow", ""),
      new HeaderEntry("connection", ""),
      new HeaderEntry("content-disposition", ""),
      new HeaderEntry("content-encoding", ""),
      new HeaderEntry("content-language", ""),
      new HeaderEntry("content-location", ""),
      new HeaderEntry("content-range", ""),
      new HeaderEntry("link", ""),
      new HeaderEntry("location", ""),
      new HeaderEntry("proxy-authenticate", ""),
      new HeaderEntry("refresh", ""),
      new HeaderEntry("retry-after", ""),
      new HeaderEntry("strict-transport-security", ""),
      new HeaderEntry("transfer-encoding", ""),
      new HeaderEntry("www-authenticate", "")
  );

  // Update these when initial tables change to sum of each entry length.
  static final int INITIAL_CLIENT_TO_SERVER_HEADER_TABLE_LENGTH = 1262;
  static final int INITIAL_SERVER_TO_CLIENT_HEADER_TABLE_LENGTH = 1304;

  static class Reader {
    private final long maxBufferSize = 4096; // TODO: needs to come from settings.
    private final DataInputStream in;

    private final BitSet referenceSet = new BitSet();
    private final List<HeaderEntry> headerTable;
    private final List<String> emittedHeaders = new ArrayList<String>();
    private long bufferSize = 0;
    private long bytesLeft = 0;

    /**
     * Read {@code byteCount} bytes of headers from the source stream into the
     * set of emitted headers.
     */
    public void readHeaders(int byteCount) throws IOException {
      bytesLeft += byteCount;
      // TODO: limit to 'byteCount' bytes?

      while (bytesLeft > 0) {
        int b = readByte();

        if ((b & 0x80) != 0) {
          int index = readInt(b, PREFIX_7_BITS);
          readIndexedHeader(index);
        } else if (b == 0x60) {
          readLiteralHeaderWithoutIndexingNewName();
        } else if ((b & 0xe0) == 0x60) {
          int index = readInt(b, PREFIX_5_BITS);
          readLiteralHeaderWithoutIndexingIndexedName(index - 1);
        } else if (b == 0x40) {
          readLiteralHeaderWithIncrementalIndexingNewName();
        } else if ((b & 0xe0) == 0x40) {
          int index = readInt(b, PREFIX_5_BITS);
          readLiteralHeaderWithIncrementalIndexingIndexedName(index - 1);
        } else if (b == 0) {
          readLiteralHeaderWithSubstitutionIndexingNewName();
        } else if ((b & 0xc0) == 0) {
          int index = readInt(b, PREFIX_6_BITS);
          readLiteralHeaderWithSubstitutionIndexingIndexedName(index - 1);
        } else {
          throw new AssertionError();
        }
      }
    }

    int readInt(int firstByte, int prefixMask) throws IOException {
      int prefix = firstByte & prefixMask;
      if (prefix < prefixMask) {
        return prefix; // This was a single byte value.
      }

      // This is a multibyte value. Read 7 bits at a time.
      int result = prefixMask;
      int shift = 0;
      while (true) {
        int b = readByte();
        if ((b & 0x80) != 0) { // Equivalent to (b >= 128) since b is in [0..255].
          result += (b & 0x7f) << shift;
          shift += 7;
        } else {
          result += b << shift; // Last byte.
          break;
        }
      }
      return result;
    }
  }

  static class Writer {
    private final OutputStream out;

    public void writeInt(int value, int prefixMask, int bits) throws IOException {
      // Write the raw value for a single byte value.
      if (value < prefixMask) {
        out.write(bits | value);
        return;
      }

      // Write the mask to start a multibyte value.
      out.write(bits | prefixMask);
      value -= prefixMask;

      // Write 7 bits at a time 'til we're done.
      while (value >= 0x80) {
        int b = value & 0x7f;
        out.write(b | 0x80);
        value >>>= 7;
      }
      out.write(value);
    }
  }
}
