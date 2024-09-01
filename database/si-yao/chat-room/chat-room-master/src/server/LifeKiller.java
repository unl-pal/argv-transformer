package server;

import utility.KVSerialize;
import utility.LogService;
import utility.SocketService;

import java.util.*;
import java.util.concurrent.*;
import java.net.*;

/**
 * This class handles heartbeats from clients.
 * If it does not receive heartbeat from a client for a certain time,
 * then it will kill the client.
 * Created by szeyiu on 3/5/15.
 */
public class LifeKiller implements Runnable{
    SocketService socketService;
    LogService logService;
    String className = "[Server][LifeKiller]";
    int KILL_TIME = 60; //kill the client if does not receive a heartbeat for 60s
    /**
     * force the client to logout to kill it.
     */
    private class Logout implements Runnable{
        String from;
    }

}
