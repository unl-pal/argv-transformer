package client;
import utility.*;
import java.net.*;
/**
 * This class is the main thread for receive the socket from p2p user or server
 * Created by szeyiu on 3/4/15.
 */
public class ReceiveService implements Runnable{
    private Thread t;
    private String threadname = "[Client][ReceiveService]";
    private static ReceiveService receiveService = null;
    private LogService logService;
    private SocketService socketService;

}
