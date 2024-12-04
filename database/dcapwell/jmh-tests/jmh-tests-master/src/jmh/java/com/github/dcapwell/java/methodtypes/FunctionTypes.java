package com.github.dcapwell.java.methodtypes;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Collections;
import java.util.List;

@State(Scope.Thread)
public class FunctionTypes {
  private final RealWork work = new RealWork();
  private final Work1 work1 = work;
  private final Work2 work2 = work;

  private interface Work1 {
  }

  private static abstract class Work2 {
  }

  private static final class RealWork extends Work2 implements Work1 {
    private static final List<String> EMPTY = Collections.emptyList();
    private final List<String> empty = Collections.emptyList();
  }
}
