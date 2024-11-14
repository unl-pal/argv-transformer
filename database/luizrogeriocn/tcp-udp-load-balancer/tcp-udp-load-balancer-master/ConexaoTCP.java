import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class ConexaoTCP implements Conexao {
	public InetAddress IPAddress;
	public int port;
	
	//Usados no tcp server
	private ServerSocket serverSocketTCP;
	private Socket connectionSocket;
	//private BufferedReader inFromClient;
	//private DataOutputStream outToClient;
	
	//usados no tcp client
	//private Socket clientSocketTCP;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	BufferedReader inFromTcpUser;
	
	//usados no tcp cantina
	private ServerSocket cantinaSocketTCP;
	//private DataOutputStream cantinaOutput;

	 
}