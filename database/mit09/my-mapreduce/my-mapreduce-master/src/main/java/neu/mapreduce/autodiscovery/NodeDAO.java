package neu.mapreduce.autodiscovery;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vishal on 4/13/15.
 * Data Access Object class for autodiscovery information. It will have
 * IP and port numbers for the autodiscovery
 */

public class NodeDAO {
    private static final Logger LOGGER = Logger.getLogger(NodeDAO.class.getName());
    public static final String RAW_REGISTRY_SPLITTER = "\\t";
    public static final String IP_PORT_SPLITTER = ":";
    public static final int ZERO = 0;
    public static final int LENGTH_ONLY_IP = 1;
    public static final int LENGTH_IP_PORTS = 3;
    public static final int LOCAL_IP_POSITION = 1;
    public static final String MULTIPLE_IP_SPLITTER = " ";

    private String ip;
    private int fileTransferPort;
    private int messagingServicePort;
}
