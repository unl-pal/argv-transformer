package com.github.dcapwell.java.file.operations.java7;

import com.github.dcapwell.java.file.operations.Files;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.github.dcapwell.java.file.operations.Chmod;
import com.github.dcapwell.java.file.operations.NIOChmod;

@State(Scope.Thread)
public class NIOFileOperations {
  private static final Chmod CHMOD = new NIOChmod();

  private final String path = Files.tmpFile().getAbsolutePath();

  @Param({"0777", "0755"})
  private int mode;
}
