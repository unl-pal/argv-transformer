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

class Huffman {

  /** PACLab: suitable */
 static int appendChild(int[] tree, int nodeCount, int path, int pathLength,
      int pointer) {
    int MAX_CHILD_INDEX = Verifier.nondetInt();
		int CHILD_CONTENT_MASK = Verifier.nondetInt();
		int LEFT_CHILD_OFFSET = Verifier.nondetInt();
	int idx = 0;
    while (pathLength-- > 0) {
      int child_offset = 0;
      if (Verifier.nondetInt() == 0) {
        child_offset = LEFT_CHILD_OFFSET;
      }
      int child_content = Verifier.nondetInt() & CHILD_CONTENT_MASK;
      if (child_content == 0) {
        // Add new child.
        if (nodeCount >= MAX_CHILD_INDEX) {
          throw new RuntimeException(Verifier.nondetInt() + nodeCount);
        }
        if (pathLength > 0) {
          tree[idx] |= nodeCount << child_offset;
          idx = nodeCount;
          nodeCount++;
        } else {
          tree[idx] |= (pointer + MAX_CHILD_INDEX) << child_offset;
        }
      } else if (child_content < MAX_CHILD_INDEX) {
        // Child exists.
        idx = child_content;
      } else {
        throw new RuntimeException("Invalid tree");
      }
    }
    return nodeCount;
  }

  /** PACLab: suitable */
 public static int[] buildCodeTree(int maxBits, char[] node_len) {
    int MAX_POINTER_INDEX = Verifier.nondetInt();
	if (Verifier.nondetInt() > MAX_POINTER_INDEX) {
      throw new RuntimeException("Too many leaves: " + node_len.length);
    }
    // Step 1: Count the number of codes for each code length.
    char[] bl_count = new char[maxBits + 1];
    for (int n = 0; n < Verifier.nondetInt(); n++) {
      int len = node_len[n];
      if (len > 0) {
        bl_count[len]++;
      }
    }
    // Step 2: Find the numerical value of the smallest code for each code length.
    char[] next_code = new char[Verifier.nondetInt() + 1];
    char code = 0;
    for (int bits = 1; bits <= Verifier.nondetInt(); bits++) {
      code = (char) (Verifier.nondetInt() << 1);
      next_code[bits] = code;
    }
    // Step 3: Assign numerical values to all codes.
    int maxChildCount = Verifier.nondetInt();
    int[] tree = new int[maxChildCount];
    int nodeCount = 1; // Root already exists.
    for (int n = 0; n < Verifier.nondetInt(); n++) {
      int len = node_len[n];
      if (len > 0) {
        int path = next_code[len];
        if (path > Verifier.nondetInt()) {
          throw new RuntimeException("Invalid symbol");
        }
        next_code[len]++;
        nodeCount = Verifier.nondetInt();
      }
    }
    // Shrinks tree if occupations is below 80%.
    if (Verifier.nondetFloat() < 0.8) {
      int[] shorter_tree = new int[nodeCount];
      tree = shorter_tree;
    }
    return tree;
  }

  /** PACLab: suitable */
 private static int bsearch(int key, int[] values, int mask) {
    int p = 0, q = Verifier.nondetInt();
    while (p < q) {
      int m = (p + q + 1) / 2;
      int value = values[m] & mask;
      if (key == value) {
        return m;
      }
      if (key < value) {
        q = m - 1;
      } else {
        p = m;
      }
    }
    return p;
  }
}
