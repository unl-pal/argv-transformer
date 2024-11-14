package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseCharacterChangeHealth;
import networking.response.ResponseCharacterCreation;

public class RequestCharacterChangeHealth extends GameRequest {

	private int healthChange;
	private ResponseCharacterChangeHealth res;

}
