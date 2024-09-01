/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The HTTP status and unparsed header fields of a single HTTP message. Values
 * are represented as uninterpreted strings; use {@link RequestHeaders} and
 * {@link ResponseHeaders} for interpreted headers. This class maintains the
 * order of the header fields within the HTTP message.
 *
 * <p>This class tracks fields line-by-line. A field with multiple comma-
 * separated values on the same line will be treated as a field with a single
 * value by this class. It is the caller's responsibility to detect and split
 * on commas if their field permits multiple values. This simplifies use of
 * single-valued fields whose values routinely contain commas, such as cookies
 * or dates.
 *
 * <p>This class trims whitespace from values. It never returns values with
 * leading or trailing whitespace.
 */
public final class RawHeaders {
  private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator<String>() {
    // @FindBugsSuppressWarnings("ES_COMPARING_PARAMETER_STRING_WITH_EQ")
    @Override public int compare(String a, String b) {
      if (a == b) {
        return 0;
      } else if (a == null) {
        return -1;
      } else if (b == null) {
        return 1;
      } else {
        return String.CASE_INSENSITIVE_ORDER.compare(a, b);
      }
    }
  };

  private final List<String> namesAndValues = new ArrayList<String>(20);
  private String requestLine;
  private String statusLine;
  private int httpMinorVersion = 1;
  private int responseCode = -1;
  private String responseMessage;

  /**
   * Returns a list of alternating names and values. Names are all lower case.
   * No names are repeated. If any name has multiple values, they are
   * concatenated using "\0" as a delimiter.
   */
  public List<String> toNameValueBlock() {
    Set<String> names = new HashSet<String>();
    List<String> result = new ArrayList<String>();
    for (int i = 0; i < namesAndValues.size(); i += 2) {
      String name = namesAndValues.get(i).toLowerCase(Locale.US);
      String value = namesAndValues.get(i + 1);

      // Drop headers that are forbidden when layering HTTP over SPDY.
      if (name.equals("connection")
          || name.equals("host")
          || name.equals("keep-alive")
          || name.equals("proxy-connection")
          || name.equals("transfer-encoding")) {
        continue;
      }

      // If we haven't seen this name before, add the pair to the end of the list...
      if (names.add(name)) {
        result.add(name);
        result.add(value);
        continue;
      }

      // ...otherwise concatenate the existing values and this value.
      for (int j = 0; j < result.size(); j += 2) {
        if (name.equals(result.get(j))) {
          result.set(j + 1, result.get(j + 1) + "\0" + value);
          break;
        }
      }
    }
    return result;
  }
}
