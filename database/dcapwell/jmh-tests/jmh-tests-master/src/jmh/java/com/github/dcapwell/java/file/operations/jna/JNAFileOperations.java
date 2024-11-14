package com.github.dcapwell.java.file.operations.jna;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.github.dcapwell.java.file.operations.Chmod;
import com.github.dcapwell.java.file.operations.Files;
import com.github.dcapwell.java.file.operations.JNAChmod;

@State(Scope.Thread)
public class JNAFileOperations {
  private static final Chmod CHMOD = new JNAChmod();

  private final String path = Files.tmpFile().getAbsolutePath();

  @Param({"0777", "0755"})
  private int mode;
}
