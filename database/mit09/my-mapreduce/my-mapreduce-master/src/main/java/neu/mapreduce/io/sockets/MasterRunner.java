package neu.mapreduce.io.sockets;

import neu.mapreduce.io.fileSplitter.SplitFile;
import neu.mapreduce.autodiscovery.NodeDAO;
import neu.mapreduce.autodiscovery.NodeRegistration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Amitash on 4/20/15. Modified by Srikar, Vishal, Mit
 */
public class MasterRunner
{
    private final static Logger LOGGER = Logger.getLogger(MasterRunner.class.getName());


    private final String jobConfClassName;
    int splitSizeInMB;
    ArrayList<String> fileSplits;
    HashMap<String, Socket> slaves;
    HashMap<String, Integer> slaveToSlavePorts;
    String inputJar;
}