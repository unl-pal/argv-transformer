package networking.request;

// Java Imports
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


// Custom Imports
import core.GameClient;
import core.GameServer;
import networking.response.GameResponse;

/**
 * The GameRequest class is an abstract class used as a basis for storing
 * request information.
 */
public abstract class GameRequest {

    protected GameClient client;
    protected DataInputStream dataInput;
    protected List<GameResponse> responses;
    protected int request_id;
    protected GameServer server;
}