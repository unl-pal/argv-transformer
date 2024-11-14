package networking.request;

import java.io.IOException;
import java.util.ArrayList;

import core.Database;
import utility.DataReader;
import model.CharacterModel;
import model.UserList;
import model.UserModel;
import networking.response.ResponseAuth;
import networking.response.ResponseCharacterCreation;
import networking.response.ResponseLogin;

public class RequestCharacterCreation extends GameRequest{
	private String cr_name;
	private int cr_type;
	private int cr_func;
	private ResponseCharacterCreation cr;
	Database db = new Database();

}
