package neu.mapreduce.autodiscovery;

/**
 * Created by vishal on 4/13/15.
 */
import neu.mapreduce.io.sockets.IOCommons;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * Finds an available port on localhost.
 * Reference: http://fahdshariff.blogspot.com/2012/10/java-find-available-port-number.html
 */
public class PortUtility {
    private static final int MIN_PORT_NUMBER = 7000;
    private static final int MAX_PORT_NUMBER = 10000;
    public static final int ONE = 1;
    private static  int nextPortNumber = MIN_PORT_NUMBER;
}
