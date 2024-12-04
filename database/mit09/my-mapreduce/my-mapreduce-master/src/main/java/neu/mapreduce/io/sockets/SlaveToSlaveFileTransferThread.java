package neu.mapreduce.io.sockets;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Amitash on 4/19/15.
 */

/**
 * Thread for slave to slave file transfer which listen on a
 * standard port and saves the files locally
 */
public class SlaveToSlaveFileTransferThread implements Runnable {
    private final int slaveToSlavePort;

    public static int fileCounter = 0;
}
