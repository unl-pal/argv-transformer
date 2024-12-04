package neu.mapreduce.core.factory;

import api.MyWriteComparable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mit, Srikar on 4/8/15.
 */

/**
 * Factory which creates {@link api.MyWriteComparable} subclass instances
 * at runtime. It creates instance only once and returns same instance
 * if called again.
 *
 * @param <T> creates instances of class T which extends {@link api.MyMapper}
 */

public final class WriteComparableFactory<T extends MyWriteComparable> {

    private final static Logger LOGGER = Logger.getLogger(WriteComparableFactory.class.getName());

    private Class<T> typeArgumentClass;
    private T singletonObject;

}