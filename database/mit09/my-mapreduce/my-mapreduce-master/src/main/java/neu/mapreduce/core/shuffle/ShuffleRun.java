package neu.mapreduce.core.shuffle;

import api.JobConf;
import api.MyWriteComparable;
import neu.mapreduce.core.combiner.CombinerRun;
import neu.mapreduce.core.factory.WriteComparableFactory;
import neu.mapreduce.io.sockets.IOCommons;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by mit, srikar on 4/8/15.
 */
public class ShuffleRun {
    private final static Logger LOGGER = Logger.getLogger(ShuffleRun.class.getName());
    public final static String INPUT_FILE_KEYVALUE_SEPARATOR = "\t";
    public final static String OUTPUT_SHUFFLE_FILE_VALUE_SEPARATOR = "\t";
    private final static String OUTPUT_SHUFFLE_FILE_LINE_SEPARATOR = "\n";
    public final static String KEY_FILENAME_MAPPING = "keyfilemapping";
    private final static int KEY_INDEX = 0;
    private final static int VALUE_INDEX = 1;
    public static final int ZERO = 0;
    public static final int TWO = 2;
}
