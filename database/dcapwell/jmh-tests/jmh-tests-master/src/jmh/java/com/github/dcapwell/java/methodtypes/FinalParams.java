package com.github.dcapwell.java.methodtypes;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Shows that final params have no effect on runtime performance
 */
public class FinalParams {
    private static final int left = ThreadLocalRandom.current().nextInt();
    private static final int right = ThreadLocalRandom.current().nextInt();
}
