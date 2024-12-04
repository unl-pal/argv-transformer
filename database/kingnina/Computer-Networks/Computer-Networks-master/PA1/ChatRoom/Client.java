import java.net.*;
import java.util.*;
import java.io.*;

public class Client {
	BufferedReader inFromServer;
	PrintWriter outToServer;
	// Socket that used to connect with server
	private static Socket socket;
	private static ServerSocket listensocket;
	// private static listenThread listen;
	private static streamThread serWriter;
	private static String uuid;

	private static String serverIP;
	private static int PORT;
	static int clientPort;
	private static int HEARTBEAT = 30;
	private static String name;
	private static String pwd;
	private static HashMap<String, String> p2pSocket = new HashMap<String, String>();

	public static class streamThread extends Thread {
		private InputStream inputStream;
		private OutputStream outputStream;
		private BufferedReader inFromServer;
		private PrintWriter outToServer;
		private Socket clientSocket;

	}

	public static class ListenThread extends Thread {
		Socket socket;
		BufferedReader inFromServer;
		HashMap<String, String> map;
	}

}
