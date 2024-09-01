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
package com.squareup.okhttp.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.squareup.okhttp.internal.Util.checkOffsetAndCount;

/**
 * An output stream wrapper that recovers from failures in the underlying stream
 * by replacing it with another stream. This class buffers a fixed amount of
 * data under the assumption that failures occur early in a stream's life.
 * If a failure occurs after the buffer has been exhausted, no recovery is
 * attempted.
 *
 * <p>Subclasses must override {@link #replacementStream} which will request a
 * replacement stream each time an {@link IOException} is encountered on the
 * current stream.
 */
public abstract class FaultRecoveringOutputStream extends AbstractOutputStream {
  private final int maxReplayBufferLength;

  /** Bytes to transmit on the replacement stream, or null if no recovery is possible. */
  private ByteArrayOutputStream replayBuffer;
  private OutputStream out;
}
