package com.github.dcapwell.java.file.operations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectChmod implements Chmod {
  private final Method chmodMethod;
}
