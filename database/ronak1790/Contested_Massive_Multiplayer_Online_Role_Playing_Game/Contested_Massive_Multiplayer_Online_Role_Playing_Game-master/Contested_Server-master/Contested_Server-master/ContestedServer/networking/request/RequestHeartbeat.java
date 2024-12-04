package networking.request;

import java.io.IOException;

import networking.response.GameResponse;
import networking.response.ResponseAuth;
import networking.response.ResponseHeartbeat;
import metadata.Constants;

public class RequestHeartbeat extends GameRequest {

	private ResponseHeartbeat reb;
	boolean op=false;

}
