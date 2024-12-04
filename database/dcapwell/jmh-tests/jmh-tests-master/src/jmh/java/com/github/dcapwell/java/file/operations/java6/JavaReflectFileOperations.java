package com.github.dcapwell.java.file.operations.java6;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.github.dcapwell.java.file.operations.Chmod;
import com.github.dcapwell.java.file.operations.Files;
import com.github.dcapwell.java.file.operations.ReflectChmod;

@State(Scope.Thread)
public class JavaReflectFileOperations {
  private static final Chmod CHMOD;

  static {
    try {
      CHMOD = new ReflectChmod();
    } catch (ClassNotFoundException e) {
      throw new AssertionError(e);
    } catch (NoSuchMethodException e) {
      throw new AssertionError(e);
    }
  }

  private final String path = Files.tmpFile().getAbsolutePath();

  @Param({"0777", "0755"})
  private int mode;
}
