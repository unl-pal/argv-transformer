package ch.florianluescher.salary;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Nullable<T> {

    class Some<T> implements Nullable<T> {

        private final T value;
    }

    class Null<T> implements Nullable<T> {
    }
}
