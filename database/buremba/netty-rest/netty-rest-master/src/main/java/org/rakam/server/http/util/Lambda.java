package org.rakam.server.http.util;

import com.google.common.base.Throwables;
import org.rakam.server.http.HttpService;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.invoke.MethodHandles.lookup;

public class Lambda {
    private final static Method biConsumerAccept;
    private final static Method consumerAccept;
    private final static Method functionApply;
    private final static Method biFunctionApply;

    static {
        try {
            biConsumerAccept = BiConsumer.class.getMethod("accept", Object.class, Object.class);
            consumerAccept = Consumer.class.getMethod("accept", Object.class);
            functionApply = Function.class.getMethod("apply", Object.class);
            biFunctionApply = BiFunction.class.getMethod("apply", Object.class, Object.class);
        } catch (NoSuchMethodException e) {
            throw Throwables.propagate(e);
        }
    }
}
