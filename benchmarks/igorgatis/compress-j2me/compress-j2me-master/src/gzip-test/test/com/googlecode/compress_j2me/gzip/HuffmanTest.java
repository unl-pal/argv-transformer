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

package com.googlecode.compress_j2me.gzip;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class HuffmanTest extends UnitTest {

  @Test
  public void testFixedLiteralsTree() {
    int[] tree = Huffman.CANONICAL_LITLENS_TREE;
    Assert.assertEquals(0, TreeNode.pointer(tree, rpath(0x30, 8)));
    Assert.assertEquals(143, TreeNode.pointer(tree, rpath(0xBF, 8)));
    Assert.assertEquals(144, TreeNode.pointer(tree, rpath(0x190, 9)));
    Assert.assertEquals(255, TreeNode.pointer(tree, rpath(0x1FF, 9)));
    Assert.assertEquals(256, TreeNode.pointer(tree, rpath(0x00, 7)));
    Assert.assertEquals(279, TreeNode.pointer(tree, rpath(0x17, 7)));
    Assert.assertEquals(280, TreeNode.pointer(tree, rpath(0xC0, 8)));
    Assert.assertEquals(287, TreeNode.pointer(tree, rpath(0xC7, 8)));
    for (int i = 0; i <= 286; i++) {
      int rpath = 0;
      if (i < 144) {
        rpath = HuffmanTest.rpath(0x30 + i, 8);
      } else if (i < 256) {
        rpath = HuffmanTest.rpath(0x190 + (i - 144), 9);
      } else if (i < 280) {
        rpath = HuffmanTest.rpath(0x00 + (i - 256), 7);
      } else {
        rpath = HuffmanTest.rpath(0xC0 + (i - 280), 8);
      }
      Assert.assertEquals("i=" + i, i, TreeNode.pointer(tree, rpath));
    }
  }

  //  @Test
  //  public void testReadLengths() throws IOException {
  //    String dynamicHuffman = "9C7D075814BDF6F72C4D44C005115151972222366C887D29"
  //        + "2AA2E262EFAE9D62C1DE75414444D4B57745EC1D3B2AEADAB1AFBD2BF6AED8BB";
  //    //pump(new GZipInputStream(h2in(dynamicHuffman)), new ByteArrayOutputStream());
  //
  //    //ZStream in = new ZStream(h2in(dynamicHuffman));
  //  }

}