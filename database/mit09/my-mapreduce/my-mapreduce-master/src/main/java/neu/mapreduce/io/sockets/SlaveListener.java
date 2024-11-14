package neu.mapreduce.io.sockets;

import api.JobConf;
import neu.mapreduce.core.factory.JobConfFactory;
import neu.mapreduce.core.shuffle.ShuffleRun;
import neu.mapreduce.autodiscovery.NodeDAO;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Amitash on 4/13/15. Modified by Srikar, Mit, Visahl
 */
public class SlaveListener {
    private static final Logger LOGGER = Logger.getLogger(SlaveListener.class.getName());

    public static String REDUCER_FOLDER_PATH;

    public static String REDUCER_CLIENT_JAR_PATH;
    public static String MAPPER_FOLDER_PATH = Constants.HOME + Constants.USER + Constants.MR_RUN_FOLDER + Constants.MAP_FOLDER;
    public static int shuffleDirCounter;
    public static ConnectionTypes status;

    private static int numMapTasks=0;

    public static String INPUT_CHUNK;
    public static String MAPPER_CLIENT_JAR_PATH;
    public static String MAP_OUTPUT_FILE_PATH;
    public static String SHUFFLE_OUTPUT_FOLDER;
    
    //This port is used for message transfer with master
    public int port;
    //This port is used to communicate with other slaves
    private int slaveToSlavePort;

    public static final int TWO = 2;
    public static final int ONE = 1;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final String MSG_SPLITTER = ":";
    private String masterIp;
}
