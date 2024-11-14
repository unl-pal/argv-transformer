package neu.mapreduce.autodiscovery;

/**
 * Created by Vishal on 4/22/15.
 */

import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class NodeRegistration {

    public static final String TRUNCATE_OPERATION_PARAM = "trunc";
    public static final String SEND_REQUEST_DATA = "";
    public static final String TRUE = "true";
    public static final String REGISTER_ME_CMD = "regme";
    public static final String REGISTRY_URL = "http://www.jquerypluginscripts.com/mr.php";
    public static final String REGISTRY_DATA_FILE = "http://jquerypluginscripts.com/nodes.txt";
    public static final String LINE_SPLITTER = "\n";
    public static final String DATA_FILE_IP_PORT_SPLITTER = "\\|";
    public static final int ZERO = 0;
    public static final String UBUNTU_NETWORK_NAME = "wlan0";
    public static final String MAC_NETWORK_NAME = "en0";
}