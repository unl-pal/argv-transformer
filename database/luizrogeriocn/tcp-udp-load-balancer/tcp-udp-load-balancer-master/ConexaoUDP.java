import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class ConexaoUDP implements Conexao {
	private InetAddress IPAddress;
	int port;
	
	private DatagramSocket cantinaSocketUDP;
	private DatagramSocket serverSocketUDP;
	private DatagramSocket clientSocketUDP;
	private DatagramPacket pacoteRecebido;
	//private DatagramPacket pacoteEnviar;
	BufferedReader inFromUdpUser;
	private byte[] serverBufferReceive = new byte[1024];
	private byte[] cantinaBufferReceive = new byte[1024];
	private byte[] clientBufferReceive = new byte[1024];
	//private byte[] serverBufferSend = new byte[1024];
	//private byte[] cantinaBufferSend = new byte[1024];
	//private byte[] clientBufferSend = new byte[1024];
		
	
	
	
}
