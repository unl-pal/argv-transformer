import java.net.*;
import java.util.*;
import java.io.*;

/**
 * CSEE4119 Computer Networks Programming Assignment 1 Chat Room
 * 
 * @author Lina Jin, Columbia University
 * @version 1.0
 */
public class Server {
	private static ServerSocket welcomeSocket;
	/**
	 * Set of user names that are online Can be used to check repeatedly log in
	 */
	private static HashMap<String, Socket> socketUser = new HashMap<String, Socket>();
	//users who failed to log in of three times
	private static HashMap<String, Long> block_list = new HashMap<String, Long>();
	// uuid identifier and username
	private static HashMap<String, String> TagUser = new HashMap<String, String>();
	private static HashMap<String, String> UserTag = new HashMap<String, String>();
	// uuid of user and the listening port of user
	private static HashMap<String, Integer> listen_id = new HashMap<String, Integer>();
	private static HashSet<String> name = new HashSet<String>();
	//Key: user identifier
 	//Value: last time when server receives a message from user
	private static HashMap<String, Long> heartbeat = new HashMap<String, Long>();
	private static long CHECK_HEARTBEAT = 35;
	long last_t;

	// Store the lists of users that a client doesn't want to contact
	private static HashMap<String, HashSet<String>> whiteList = new HashMap<String, HashSet<String>>();

	// Store offline Message
	private static HashMap<String, ArrayList<String>> offMsg = new HashMap<String, ArrayList<String>>();
	private static ArrayList<String> off_msg = new ArrayList<String>();
	private static String[] split;

	public static class ConnectionThread extends Thread {
		Socket connectionSocket;
		BufferedReader inFromClient;
		PrintWriter outToClient;
		String userName;
		String passWord;

		public void run() {
			// create streams for the socket
			try {
				int BLOCK_TIME = 60;
				long start;
				inFromClient = new BufferedReader(new InputStreamReader(
						connectionSocket.getInputStream()));
				outToClient = new PrintWriter(
						connectionSocket.getOutputStream(), true);
				/**
				 * requesting the name and password from the client repeatedly
				 * not that after 3 consecutive failed attempts, the user is
				 * blocked for a duration of 60 seconds
				 */
				boolean authen = false;
				String line = "";
				int i = 0;
				// read from the Client
				line = inFromClient.readLine();
				System.out.println(line);
				split = line.split(" ");
				long current_t = System.currentTimeMillis();
				String usr = split[0];
				if (!line.startsWith("USERNAME")){
					if (heartbeat.containsKey(usr)){
						heartbeat.remove(usr);
						heartbeat.put(usr, current_t);
					}else{
						heartbeat.put(usr, current_t);
					}
				}
				if (line.startsWith("USERNAME")) {
					while (!authen) {
						i = i + 1;
						String[] sp = line.split(" ");
						userName = sp[1];
						passWord = sp[2];
						String name_pwd = userName + " " + passWord;
						// stop the user from logging in if the user is in the block_list
						if (block_list.containsKey(userName)
								&& (System.currentTimeMillis()
										- (block_list.get(userName)) > BLOCK_TIME * 1000)) {
							synchronized (block_list) {
								block_list.remove(userName);
							}
						}
						if (block_list.containsKey(userName)) {
							outToClient.println(3);
							break;
						}
						int value = readCredentials(name_pwd);
						// correct credentials
						if (value == 1) {			
							// log out user with same username online
							if (socketUser.containsKey(userName)) {
								System.out.println(socketUser);
								this.logoutExistingUser(userName);
							}
							// update data structures
							synchronized (socketUser) {
								socketUser.put(userName, this.connectionSocket);
							}
							// GENERATE the unique IDENTIFIER for this user
							UUID uuid = UUID.randomUUID();
							synchronized (TagUser) {
								TagUser.put(uuid.toString(), userName);
							}
							synchronized (UserTag) {
								UserTag.put(userName, uuid.toString());
							}
							for (String key : socketUser.keySet()) {
								name.add(key);
							}
							for (String key : socketUser.keySet()) {
								synchronized (whiteList) {
									whiteList.put(key, name);
								}
							}
							outToClient.println("0 "+uuid);
							line = inFromClient.readLine();
							if (line.startsWith("CLIENTPORT")){
								String[] l = line.split(" ");
								String id = l[1];
								int port = Integer.parseInt(l[2]);
								listen_id.put(id, port);
							}
							this.onlineReport(userName);
							this.sendOfflineMsg();
							break;
						}
						// wrong credentials
						if (value == 0) {
							// first and second failed login
							if (i == 1 || i == 2) {
								outToClient.println(1);
							}
							// third time failed login, block user
							if (i == 3) {
								start = System.currentTimeMillis();
								synchronized (block_list) {
									block_list.put(userName, start);
									outToClient.println(2);
								}
							}
						}
						line = inFromClient.readLine();
					}
				}
				// UUID + "message" + receiver + msg
				if (split[1].equals("message")) {
					String sender = TagUser.get(split[0]);
					String msg = sender
							+ ": "
							+ line.replaceAll(split[0] + " " + split[1] + " "+ split[2] + " ", "");
					String recipient = split[2];
					if (whiteList.get(recipient) == null
							|| whiteList.get(recipient).contains(sender)) {
						if (socketUser.containsKey(recipient)) {
							this.newConnection(msg, recipient);
						} else {
							// Keep the offline message record
							this.offlineMessage(recipient, msg);
						}
					}
					if ((whiteList.get(recipient) != null) && (!whiteList.get(recipient).contains(sender))) {
						this.newConnection("Your message could not be delivered as the recipient has blocked you", sender);
					}
				}
				if (split[1].equals("block")) {
					String block_name = split[2];
					String sender = TagUser.get(split[0]);
					this.newConnection("BLOCK "+block_name+" done.", sender);
					this.block(sender, block_name);
				}
				if (split[1].equals("unblock")) {
					String unblock_name = split[2];
					String sender = TagUser.get(split[0]);
					synchronized (whiteList) {
						whiteList.get(sender).add(unblock_name);
					}
					String msg = "User " + unblock_name + " has been unblocked";
					this.newConnection(msg, sender);
				}
				//broadcast messages to users that doesn't block the sender
				if (split[1].equals("broadcast")) {
					String msg = line.replaceAll(split[0] + " broadcast ", "");
					String sender = TagUser.get(split[0]);
					for (String key : socketUser.keySet()) {
						if (whiteList.containsKey(key)
								&& whiteList.get(key).contains(sender)) {
							this.newConnection(sender+ ": "+ msg, key);
						}
						if (!whiteList.containsKey(key)) {
							this.newConnection(sender + ": " + msg, sender);
						}
						if (!whiteList.get(key).contains(sender)) {
							this.newConnection("Your message could not be delivered to some recipients", sender);
						}

					}
				}
				// print the list of all online users
				if (split[1].equals("online")) {
					String sender = TagUser.get(split[0]);
					for (String key : socketUser.keySet()) {
						this.newConnection(key, sender);
					}
				}
				if (split[1].equals("getaddress")) {
					String user = TagUser.get(split[0]);
					String r = split[2];
					String rcvID = UserTag.get(r);
					// Consider the block scenario and if the user is online or not
					if (socketUser.containsKey(r)){
						if (whiteList.get(r).contains(user) || (!whiteList.containsKey(r))){
							String IP = socketUser.get(r).getInetAddress().getHostAddress();
							String msg = "P2P "+r+" IP: "+IP+" PORT: "+listen_id.get(rcvID);
							this.newConnection(msg, user);
						}else{
							this.newConnection("The IP address and PORT number of "+r+" are not reachable", user);
						}
					}else
						this.newConnection("Request Failure",user);
				}
					
				if (split[1].equals("logout")) {
					String sender = TagUser.get(split[0]);
					this.newConnection("logout", sender);
					try {
						this.connectionSocket.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					synchronized (whiteList) {
						whiteList.remove(sender);
					}
					synchronized (TagUser) {
						TagUser.remove(UserTag.get(sender));
					}
					synchronized (UserTag) {
						UserTag.remove(sender);
					}
					synchronized (socketUser) {
						socketUser.remove(sender);
					}
					this.offlineReport(sender);
				}
				this.connectionSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * The checkHeartBeat extends Thread which runs every 35 seconds
	 * to check the timeout conditions of each client
	 */
	private static class checkHeartBeat extends Thread{
		public void run(){
			try {
				Thread.sleep(CHECK_HEARTBEAT*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long time = System.currentTimeMillis();
			synchronized(heartbeat){
				for(String key: heartbeat.keySet()){
					if ((time-heartbeat.get(key))>CHECK_HEARTBEAT*1000){
						this.connDisconnect(key);
					}
				}
			}
		}

}
}
