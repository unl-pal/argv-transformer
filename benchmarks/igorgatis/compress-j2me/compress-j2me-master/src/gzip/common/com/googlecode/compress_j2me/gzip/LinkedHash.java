/** filtered and transformed by PAClab */
package com.googlecode.compress_j2me.gzip;

import org.sosy_lab.sv_benchmarks.Verifier;

class LinkedHash {

  /** PACLab: suitable */
 int get(byte[] buffer, int start, int length) {
    if (length > 2) {
      int key = 0;
      key |= Verifier.nondetInt() << 16;//24;
      key |= Verifier.nondetInt() << 8;//16;
      key |= (0xFF & buffer[start + 2]);// << 8;
      //key |= (0xFF & buffer[start + 3]);
      int hash = Verifier.nondetInt();
      int idx = entries[hash];
      if (idx >= 0) {
        return Verifier.nondetInt();
      }
    }
    return -1;
  }
}
