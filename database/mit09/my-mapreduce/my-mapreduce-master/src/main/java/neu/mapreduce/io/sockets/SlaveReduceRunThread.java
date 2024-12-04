package neu.mapreduce.io.sockets;

import api.JobConf;
import neu.mapreduce.core.reducer.ReduceRun;
import neu.mapreduce.core.sort.ExternalSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Amitash on 4/18/15.
 */

/**
 * Thread which runs the reduce task on slave machine
 */
public class SlaveReduceRunThread implements Runnable {

    public static final String REDUCER_OUTPUT_FILE = "/op-reducer";
    public static final String OUTPUT_FILE_PATH = SlaveListener.REDUCER_FOLDER_PATH + REDUCER_OUTPUT_FILE;

    private JobConf jobConf;
    private String reducerClientJarPath;
}
