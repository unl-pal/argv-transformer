import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


public class Mensagem {
	
	private InetAddress IPAddress;
	private byte[] sendData;
	private int porta;
	private DatagramSocket msgSocket;
	
	private String mensagemFormatada;
	private String[] msg;
	private String id;
	private String acao;
	private String deQuem;
	private int numero;
	private String local;
	
}