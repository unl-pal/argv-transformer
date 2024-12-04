package ch.florianluescher.salary;

import java.util.function.Supplier;

public interface Try<T> {

    class Success<T> implements Try<T> {

        private final T value;
    }

    class Failure<T> implements Try<T> {
        private Exception catchedException;
    }
}
