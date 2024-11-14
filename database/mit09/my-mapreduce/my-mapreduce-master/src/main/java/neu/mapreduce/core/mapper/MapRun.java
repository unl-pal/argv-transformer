package neu.mapreduce.core.mapper;

import api.JobConf;
import api.MyContext;
import neu.mapreduce.core.factory.MapFactory;
import neu.mapreduce.core.factory.WriteComparableFactory;
import neu.mapreduce.core.shuffle.ShuffleRun;
import neu.mapreduce.io.sockets.IOCommons;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mit and srikar on 4/9/15.
 */
/**
 * Class which is responsible for performing map and shuffle phase
 */

public class MapRun {

    private final static Logger LOGGER = Logger.getLogger(MapRun.class.getName());
}