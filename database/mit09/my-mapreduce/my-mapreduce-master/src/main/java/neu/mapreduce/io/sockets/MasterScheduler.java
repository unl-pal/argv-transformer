package neu.mapreduce.io.sockets;

import api.JobConf;
import neu.mapreduce.autodiscovery.NodeRegistration;
import neu.mapreduce.core.factory.JobConfFactory;
import neu.mapreduce.core.shuffle.ShuffleRun;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Amitash on 4/18/15. Modified by Srikar, Vishal, Mit
 */
public class MasterScheduler {

    public static final int MASTER_FT_PORT_MAPPER = 6060;
    public static final int MASTER_FT_PORT_REDUCER = 6061;

    private static final Logger LOGGER = Logger.getLogger(MasterScheduler.class.getName());
    public static int keyMappingFileCounter = 0;
    public static final String KEY_MAPPING_FILE = Constants.HOME+Constants.USER+Constants.MR_RUN_FOLDER+Constants.MASTER_FOLER +"/keyMapping";
    private  HashMap<String, Integer> slaveToSlavePorts;


    public String masterIP;
    private ArrayList<String> fileSplits;
    private String inputJar;
    private HashMap<String, Socket> slaves;
    private Job job;
    private JobConf jobConf;
    private String jobConfClassName;
    //freeSlaveID is a string in the format: ip:port. eg: 192.168.1.1:8087
    private String freeSlaveID;
    private String curSplit;
    private HashMap<String, ArrayList<String>> keyFileMapping;

}

