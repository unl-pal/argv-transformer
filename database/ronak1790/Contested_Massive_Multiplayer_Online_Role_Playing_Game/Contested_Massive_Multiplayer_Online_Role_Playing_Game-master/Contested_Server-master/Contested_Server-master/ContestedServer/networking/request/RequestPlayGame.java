package networking.request;

import java.io.IOException;

import core.Database;
import utility.DataReader;
import model.CharacterModel;
import model.UserModel;
import networking.response.ResponseAuth;
import networking.response.ResponseCharacterCreation;
import networking.response.ResponsePlayGame;

public class RequestPlayGame extends GameRequest{

	
	private int cr_id;
	private String cr_name;
	private int cr_type;
	private int cr_func;
	private ResponsePlayGame cr;
	Database db;

}
