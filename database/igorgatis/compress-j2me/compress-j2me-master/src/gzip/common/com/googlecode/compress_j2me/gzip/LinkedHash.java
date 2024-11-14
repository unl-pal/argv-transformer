package com.googlecode.compress_j2me.gzip;

class LinkedHash {

  private static final int HASH_PRIME = 31;

  private short[] entries;
  private int[] keys;
  private int[] markers;
  private int size;

  int get(byte[] buffer, int start, int length) {
    if (length > 2) {
      int key = 0;
      key |= (0xFF & buffer[start]) << 16;//24;
      key |= (0xFF & buffer[start + 1]) << 8;//16;
      key |= (0xFF & buffer[start + 2]);// << 8;
      //key |= (0xFF & buffer[start + 3]);
      int hash = find(key);
      int idx = this.entries[hash];
      if (idx >= 0) {
        return this.markers[idx];
      }
    }
    return -1;
  }
}
