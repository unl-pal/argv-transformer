package core;

// Java Imports
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;






import java.util.List;

import org.omg.CORBA.Request;



// Custom Imports
import configuration.GameServerConf;
//import dataAccessLayer.DAO;
import metadata.GameRequestTable;
import model.CharacterModel;
import model.ControlPointModel;
import model.Npc;
import model.UserModel;
//import model.Player;
import networking.response.GameResponse;
import utility.ConfFileParser;

/**
 * The GameServer class serves as the main module that runs the server.
 * Incoming connection requests are established and redirected to be managed
 * by another class called the GameClient. Several specialized methods are also
 * stored here to perform other specific needs.
 */
public class GameServer {

    private static GameServer gameServer; // References GameServer instance
    private GameServerConf configuration; // Stores server config. variables
    private boolean ready = false; // Used to keep server looping
    private HashMap<Long, GameClient> activeThreads = new HashMap<Long, GameClient>(); // Stores active threads by thread ID
    private ArrayList<ControlPointModel> cplist;
    private HashMap<Integer, UserModel> activePlayers = new HashMap<Integer, UserModel>(); // Stores active players by player ID
}
