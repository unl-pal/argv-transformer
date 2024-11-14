package networking.request;

import java.io.IOException;

import core.Database;
import utility.DataReader;
import networking.response.ResponseAuth;
import networking.response.ResponseRegistration;

public class RequestRegistration extends GameRequest{

	private String Username;
	private String Password;
	private ResponseRegistration res;
	Database db = new Database();

}
