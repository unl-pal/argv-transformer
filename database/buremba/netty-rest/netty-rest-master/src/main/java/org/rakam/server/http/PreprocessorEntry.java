package org.rakam.server.http;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class PreprocessorEntry {
   private final RequestPreprocessor preprocessor;
   private final Predicate<Method> predicate;
}
