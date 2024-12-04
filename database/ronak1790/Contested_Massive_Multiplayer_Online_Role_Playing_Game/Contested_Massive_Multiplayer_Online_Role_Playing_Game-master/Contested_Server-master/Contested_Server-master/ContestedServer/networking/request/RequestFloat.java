package networking.request;

// Java Imports
import java.io.IOException;

// Custom Imports
//import core.GameServer;
import networking.response.ResponseFloat;
import utility.DataReader;

public class RequestFloat extends GameRequest {

    // Data
    private float number;
    // Responses
    private ResponseFloat responseFloat;
}
