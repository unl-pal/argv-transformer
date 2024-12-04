package networking.request;

import java.io.IOException;
import java.util.ArrayList;

import core.Database;
import core.GameClient;
import core.GameServer;
import utility.DataReader;
import networking.response.ResponseAuth;
import networking.response.ResponseLogin;
import metadata.Constants;
import model.CharacterModel;
import model.UserList;
import model.UserModel;

public class RequestLogin extends GameRequest{

    public static String username;
	private String password;
	private ResponseAuth res;
	boolean en = false;
	
	Database db = new Database();
	
}
