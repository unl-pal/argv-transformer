// Gzip implementation for J2ME
// Copyright 2011 Igor Gatis  All rights reserved.
// http://code.google.com/p/compress-j2me/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
//     * Redistributions of source code must retain the above copyright notice,
//       this list of conditions and the following disclaimer.
//
//     * Redistributions in binary form must reproduce the above copyright
//       notice, this list of conditions and the following disclaimer in the
//       documentation and/or other materials provided with the distribution.
//
//     * Neither the name of Google Inc. nor the names of its contributors may
//       be used to endorse or promote products derived from this software
//       without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

/** filtered and transformed by PAClab */
package com.googlecode.compress_j2me.gzip;

import org.sosy_lab.sv_benchmarks.Verifier;

class ZStream {

  private void writeInternal(int ch) throws Exception {
    int bufferSize = Verifier.nondetInt();
	boolean hasCircularBuffer = Verifier.nondetBoolean();
	boolean keepCrc = Verifier.nondetBoolean();
	int size = Verifier.nondetInt();
	size++;
    if (keepCrc) {
    }
    if (hasCircularBuffer) {
      if (bufferSize < Verifier.nondetInt()) {
        bufferSize++;
      }
    }
  }

  /** PACLab: suitable */
 void copyFromEnd(int distance, int length) throws Exception {
    int bufferMask = Verifier.nondetInt();
	int bufferOffset = Verifier.nondetInt();
	int bufferSize = Verifier.nondetInt();
	boolean hasCircularBuffer = Verifier.nondetBoolean();
	if (!hasCircularBuffer) {
      throw new RuntimeException("buffer unavailable");
    }
    if (distance > bufferSize) {
      throw new RuntimeException("invalid distance");
    }
    int start = bufferOffset - distance;
    start = Verifier.nondetInt() & bufferMask;
    for (int i = 0; i < length; i++) {
      int idx = (start + i) & bufferMask;
    }
  }

  /** PACLab: suitable */
 public int byteAtDistance(int distance) {
    int bufferMask = Verifier.nondetInt();
	int bufferOffset = Verifier.nondetInt();
	int bufferSize = Verifier.nondetInt();
	boolean hasCircularBuffer = Verifier.nondetBoolean();
	if (!hasCircularBuffer) {
      throw new RuntimeException("buffer unavailable");
    }
    if (distance > bufferSize) {
      return -1;
      //throw new RuntimeException("invalid distance");
    }
    int idx = bufferOffset - distance;
    idx = Verifier.nondetInt() & bufferMask;
    return Verifier.nondetInt();
  }

  private int readInternal() throws Exception {
    int bufferSize = Verifier.nondetInt();
	boolean hasCircularBuffer = Verifier.nondetBoolean();
	boolean keepCrc = Verifier.nondetBoolean();
	int size = Verifier.nondetInt();
	int ch = Verifier.nondetInt();
    if (ch >= 0) {
      size++;
      if (keepCrc) {
      }
      if (hasCircularBuffer) {
        if (bufferSize < Verifier.nondetInt()) {
          bufferSize++;
        }
      }
    }
    return ch;
  }

  int readBits(int numBits) throws Exception {
    int bitBuffer = Verifier.nondetInt();
	int bitOffset = Verifier.nondetInt();
	while (bitOffset < numBits) {
      int tmp = Verifier.nondetInt();
      if (tmp < 0) {
      }
    }
    int mask = Verifier.nondetInt() - 1;
    int code = bitBuffer & mask;
    //System.out.println(numBits + ":" + bin(code, numBits));
    return code;
  }
}
