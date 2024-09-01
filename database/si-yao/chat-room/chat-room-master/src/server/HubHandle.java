package server;

import utility.*;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;
/**
 * This is the thread to handle the incoming request.
 * Created by szeyiu on 3/4/15.
 */
public class HubHandle implements Runnable{
    private Socket src;
    SocketService socketService;
    LogService logService;
    String className = "[Server][HubHanle]";
    /**
     * When a user logged out, then the server sends notification to every user
     * who has a p2p connection with the user.
     */
    private class OfflineMsg implements Runnable{
        String from;
        String u;
    }

    /**
     * This is the thread to sent the broadcast message.
     * Using a runnable class could make it concurrent.
     */
    private class GroupMsg implements Runnable{
        String req;
        String to;
    }

}
