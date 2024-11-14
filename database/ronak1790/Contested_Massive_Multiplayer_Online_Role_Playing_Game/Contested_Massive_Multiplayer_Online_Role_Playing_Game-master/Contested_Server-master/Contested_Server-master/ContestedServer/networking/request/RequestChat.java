package networking.request;

import java.io.IOException;

import model.CharacterModel;
import networking.response.ResponseChat;
import utility.DataReader;

public class RequestChat extends GameRequest{


    // Data
    private String message;
    private String username;
    private int teamid;

    // Responses
    private ResponseChat responseChat;

}
