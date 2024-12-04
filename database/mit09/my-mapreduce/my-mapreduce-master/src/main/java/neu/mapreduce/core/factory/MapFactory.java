package neu.mapreduce.core.factory;

import api.MyMapper;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Mit, Srikar on 4/8/15.
 */

/**
 * Factory which creates {@link api.MyMapper} subclass instances at runtime. It creates
 * instance only once and returns same instance if called again.
 *
 * @param <T> creates instances of class T which extends {@link api.MyMapper}
 */

public final class MapFactory<T extends MyMapper> {

    private Class<T> typeArgumentClass;
    private T singletonObject;
}