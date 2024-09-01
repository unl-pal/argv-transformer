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

import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * Reads a SPDY/3 Name/Value header block. This class is made complicated by the
 * requirement that we're strict with which bytes we put in the compressed bytes
 * buffer. We need to put all compressed bytes into that buffer -- but no other
 * bytes.
 */
class NameValueBlockReader implements Closeable {
  private final DataInputStream nameValueBlockIn;
  private final FillableInflaterInputStream fillableInflaterInputStream;
  private int compressedLimit;

  /** Extend the inflater stream so we can eagerly fill the compressed bytes buffer if necessary. */
  static class FillableInflaterInputStream extends InflaterInputStream {
  }

  public List<String> readNameValueBlock(int length) throws IOException {
    this.compressedLimit += length;
    try {
      int numberOfPairs = nameValueBlockIn.readInt();
      if (numberOfPairs < 0) {
        throw new IOException("numberOfPairs < 0: " + numberOfPairs);
      }
      if (numberOfPairs > 1024) {
        throw new IOException("numberOfPairs > 1024: " + numberOfPairs);
      }
      List<String> entries = new ArrayList<String>(numberOfPairs * 2);
      for (int i = 0; i < numberOfPairs; i++) {
        String name = readString();
        String values = readString();
        if (name.length() == 0) throw new IOException("name.length == 0");
        entries.add(name);
        entries.add(values);
      }

      doneReading();

      return entries;
    } catch (DataFormatException e) {
      throw new IOException(e.getMessage());
    }
  }
}
