package neu.mapreduce.core.combiner;

import api.MyContext;
import neu.mapreduce.core.factory.CombinerFactory;
import neu.mapreduce.core.factory.ReducerFactory;
import neu.mapreduce.core.factory.WriteComparableFactory;

import java.io.BufferedWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by Mit on 4/18/15.
 */
public class CombinerRun {
    private final static Logger LOGGER = Logger.getLogger(CombinerRun.class.getName());
    private final static String KEY_VALUE_SEPARATOR = "\n";
}


