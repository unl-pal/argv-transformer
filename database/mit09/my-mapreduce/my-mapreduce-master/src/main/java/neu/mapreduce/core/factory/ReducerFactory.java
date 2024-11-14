package neu.mapreduce.core.factory;

import api.MyMapper;
import api.MyReducer;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Mit, Srikar on 4/8/15.
 */

public final class ReducerFactory<T extends MyReducer> {

    private Class<T> typeArgumentClass;
    private T singletonObject;
}