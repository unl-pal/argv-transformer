package networking.request;

import java.io.IOException;

import utility.DataReader;
import networking.response.ResponseControlPointState;

public class RequestControlPointState extends GameRequest{

	private int controlPointId ;
	private int controlPointState ;
	private ResponseControlPointState res;

}
