package br.com.ws;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

//ws://localhost:8080/websocket/echo/wallace
@ServerEndpoint(value = "/echo/{nome}")
public class EchoEndpoint {

}
