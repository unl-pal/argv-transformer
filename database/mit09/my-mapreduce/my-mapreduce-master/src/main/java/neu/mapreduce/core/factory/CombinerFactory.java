package neu.mapreduce.core.factory;

import api.MyCombiner;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Mit, Srikar on 4/18/15.
 */

/**
 * Factory which creates {@link api.MyCombiner} subclass instances at runtime. It creates
 * instance only once and returns same instance if called again.
 *
 * @param <T> creates instances of class T which extends {@link api.MyCombiner}
 */

public class CombinerFactory<T extends MyCombiner> {

    private Class<T> typeArgumentClass;
    private T singletonObject;
}
