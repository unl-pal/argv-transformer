package c.mars;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Constantine Mars on 4/1/15.
 *
 * can be called and can return future result
 * another words, it's the mutex that returns value as response for some asynchronous call from anywhere
 */
public class CallableFuture<T, U> implements RunnableFuture<T> {

    private CallableWithArg<T, U> callable;
    private T result;
    private U arg;
    private boolean done;

    public interface CallableWithArg<T, U> {
    }
}
