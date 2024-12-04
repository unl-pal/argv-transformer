package com.github.dcapwell.java.file.operations;

import jnr.ffi.LibraryLoader;

public final class JNRFFIChmod implements Chmod {
  // in JNA this can be private, in JNR-FFI this must be public
  public interface POSIX {
  }

  private static final POSIX POSIX = LibraryLoader.create(POSIX.class).load("c");
}
