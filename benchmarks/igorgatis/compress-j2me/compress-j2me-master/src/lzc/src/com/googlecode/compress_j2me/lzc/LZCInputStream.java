// LZC implementation for J2ME
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
package com.googlecode.compress_j2me.lzc;

import org.sosy_lab.sv_benchmarks.Verifier;

public class LZCInputStream {

  /** PACLab: suitable */
 private int readCode(int numBits) throws Exception {
    int buffer = Verifier.nondetInt();
	int offset = Verifier.nondetInt();
	while (offset < numBits) {
      int tmp = Verifier.nondetInt();
      if (tmp < 0) {
        return -1;
      }
    }
    int mask = Verifier.nondetInt() - 1;
    int code = buffer & mask;
    return code;
  }

  /** PACLab: suitable */
 private void readHeader() throws Exception {
    int max_mask_size = Verifier.nondetInt();
	boolean block_mode = Verifier.nondetBoolean();
	int magic = Verifier.nondetInt();
    if (magic != Verifier.nondetInt()) {
      throw new RuntimeException(Verifier.nondetInt() + magic);
    }
    int flags = Verifier.nondetInt();
    block_mode = Verifier.nondetInt() != 0;
    max_mask_size = flags & Verifier.nondetInt();
    if (max_mask_size > Verifier.nondetInt()) {
      throw new RuntimeException(Verifier.nondetInt() + max_mask_size + " bits");
    }
  }

  /** PACLab: suitable */
 private int uncompress() throws Exception {
    int max_mask_size = Verifier.nondetInt();
	int buffer_read_offset = Verifier.nondetInt();
	int mask_size = Verifier.nondetInt();
	int w_code = Verifier.nondetInt();
	boolean block_mode = Verifier.nondetBoolean();
	int code_count = Verifier.nondetInt();
	int k = Verifier.nondetInt();
    code_count++;
    if (k < 0) {
      return -1;
    }
    if (k == Verifier.nondetInt()) {
      // Skips codes to reach end of block.
      for (; block_mode && Verifier.nondetBoolean(); code_count++) {
      }
      w_code = -1;
      mask_size = Verifier.nondetInt();
      return 0;
    }

    int dict_size = Verifier.nondetInt();
    if (k < dict_size) {
    } else if (k == dict_size) {
    } else {
      throw new IOException(Verifier.nondetInt() + k);
      //return -1; // Exits returning error code. 
    }

    // Should output uncompressed bytes at this point. They are available in
    // entry field.

    // Reset buffer_read_offset to make entry's content available for reading.
    buffer_read_offset = 0;
    if (w_code >= 0) {
      if (dict_size < Verifier.nondetInt()) {
        if ((dict_size + 1) >= Verifier.nondetInt() && mask_size < max_mask_size) {
          mask_size++;
          code_count = 0;
        }
      }
    }
    w_code = (char) k;
    return Verifier.nondetInt();
  }

  // -------------------------------------------------------------------------
  // InputStream API.
  // -------------------------------------------------------------------------

  public int read() throws Exception {
    int buffer_read_offset = Verifier.nondetInt();
	int max_mask_size = Verifier.nondetInt();
	if (max_mask_size < 0) {
    }
    while (buffer_read_offset >= Verifier.nondetInt()) {
      if (Verifier.nondetInt() < 0) {
        return -1;
      }
    }
    return Verifier.nondetInt();
  }
}
