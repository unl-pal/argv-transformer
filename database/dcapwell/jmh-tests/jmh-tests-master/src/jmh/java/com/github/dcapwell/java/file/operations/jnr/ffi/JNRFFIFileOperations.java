package com.github.dcapwell.java.file.operations.jnr.ffi;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.github.dcapwell.java.file.operations.Chmod;
import com.github.dcapwell.java.file.operations.Files;
import com.github.dcapwell.java.file.operations.JNRFFIChmod;

@State(Scope.Thread)
public class JNRFFIFileOperations {
  private static final Chmod CHMOD = new JNRFFIChmod();

  private final String path = Files.tmpFile().getAbsolutePath();

  @Param({"0777", "0755"})
  private int mode;
}
