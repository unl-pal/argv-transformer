package networking.request;

import java.io.IOException;

import utility.DataReader;
import model.UserList;
import model.UserModel;
import networking.response.ResponseCharacterMovement;
import networking.response.ResponsePlayGame;
import networking.response.ResponseRegistration;

public class RequestMove extends GameRequest{

	
	private float x;
	private float y;
	private float z;
	private float h;
	private int isMoving;
	
	private ResponseCharacterMovement res;

}
