package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseCharacterAttack;
import networking.response.ResponseHeartbeat;

public class RequestCharacterAttack extends GameRequest{

	private int attackId;
	private ResponseCharacterAttack res;

}
