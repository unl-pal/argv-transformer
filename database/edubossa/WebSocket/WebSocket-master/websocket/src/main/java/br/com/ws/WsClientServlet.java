package br.com.ws;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Servlet implementation class WsClientServlet
 * <p>
 * https://github.com/TooTallNate/Java-WebSocket
 * <p>
 * http://localhost:8080/websocket/wsclient
 * <p>
 * http://www.websocket.org/echo.html
 * <p>
 * http://localhost:8080/websocket/index.html
 */
@WebServlet(urlPatterns = "/wsclient")
public class WsClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

}
