package neu.mapreduce.io.sockets;

import api.JobConf;
import neu.mapreduce.core.mapper.MapRun;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Amitash on 4/19/15.
 */

/**
 * Thread which runs the mapper task on slave machine
 */
public class SlaveMapRunThread implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SlaveMapRunThread.class.getName());

    private String inputFilePath;
    private String mapOutputFilePath;
    private String shuffleOutputFolder;
    private String clientJarPath;
    private JobConf jobConf;
}
