package com.github.dcapwell.java.file.operations.fork;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.github.dcapwell.java.file.operations.Chmod;
import com.github.dcapwell.java.file.operations.Files;
import com.github.dcapwell.java.file.operations.ForkChmod;

@State(Scope.Thread)
public class ForkFileOperations {
  private static final Chmod CHMOD = new ForkChmod();

  private final String path = Files.tmpFile().getAbsolutePath();

  @Param({"0777", "0755"})
  private int mode;
}
