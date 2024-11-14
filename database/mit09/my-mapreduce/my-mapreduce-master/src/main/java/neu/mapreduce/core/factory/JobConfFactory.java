package neu.mapreduce.core.factory;

/**
 * Created by Mit, Srikar on 4/20/15.
 */

import api.JobConf;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Factory which creates {@link api.JobConf} subclass instances at runtime. It creates
 * instance only once and returns same instance if called again.
 *
 * @param <T> creates instances of class T which extends {@link api.JobConf}
 */

public final class JobConfFactory<T extends JobConf> {

    private Class<T> typeArgumentClass;
    private T singletonObject;
}
