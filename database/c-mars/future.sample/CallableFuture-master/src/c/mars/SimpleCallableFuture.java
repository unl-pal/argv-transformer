package c.mars;

/**
 * Created by Constantine Mars on 4/1/15.
 *
 * simplified version of CallableFuture used with only one type of in and out values
 */
public class SimpleCallableFuture<T> extends CallableFuture<T, T> {
    public static class SimpleCallableWithArg<T> implements CallableWithArg<T, T> {
    }
}
