package com.github.dcapwell.java.file.operations;

import com.sun.jna.Library;
import com.sun.jna.Native;

public final class JNAChmod implements Chmod {
  private interface POSIX extends Library {
  }

  private static final POSIX POSIX = (POSIX) Native.loadLibrary("c", POSIX.class);
}
