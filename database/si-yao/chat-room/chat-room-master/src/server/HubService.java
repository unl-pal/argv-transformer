package server;

import utility.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
/**
 * This is hte main thread for incoming requests.
 * Created by szeyiu on 3/4/15.
 */
public class HubService implements Runnable{
    private Thread t;
    private String threadname = "[Server][HubService]";
    private static HubService hubService = null;
    private LogService logService;
    private SocketService socketService;
    //following *Map members are info of users.
    public static Map<String, String> passwdMap;
    public static Map<String, String> ipMap;
    public static Map<String, Integer> portMap;
    public static Map<String, List<String>> blockMap;
    public static Map<String, List<String>> offlineReq;
    public static Map<String, Boolean> aliveMap;
    public static Map<String, Set<String>> p2pPairs;
    public static int serverPort;
    private String passwdFile = "credentials.txt";
}
