/**
 * Copyright (c) 2010 Yahoo! Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *                                                              
 * http://www.apache.org/licenses/LICENSE-2.0
 *                                                            
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */
package com.yahoo.ycsb;

/**
 *  A ByteIterator that generates a random sequence of bytes.
 */
public class RandomByteIterator extends ByteIterator {
  private long len;
  private long off;
  private int bufOff;
  private byte[] buf;

  @Override
  public int nextBuf(byte[] buffer, int bufferOffset) {
    int ret;
    if(len - off < buffer.length - bufferOffset) {
      ret = (int)(len - off);
    } else {
      ret = buffer.length - bufferOffset;
    }
    int i;
    for(i = 0; i < ret; i+=6) {
      fillBytesImpl(buffer, i + bufferOffset);
    }
    off+=ret;
    return ret + bufferOffset;
  }
}
