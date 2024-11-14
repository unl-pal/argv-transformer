package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseCharacterAttack;
import networking.response.ResponseControlPointCapture;

public class RequestControlPointCapture extends GameRequest{

	private  int controlPointId;
	private int factionId;
	private ResponseControlPointCapture res;

}
