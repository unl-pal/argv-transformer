package core;

// Java Imports
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
//import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;





// Custom Imports
//import dataAccessLayer.PlayerDAO;
import metadata.Constants;
import metadata.GameRequestTable;
import model.CharacterModel;
import model.UserModel;
//import model.Player;
import networking.request.GameRequest;
import networking.request.RequestLogin;
import networking.response.GameResponse;
import utility.DataReader;

/**
 * The GameClient class is an extension of the Thread class that represents an
 * individual client. Not only does this class holds the connection between the
 * client and server, it is also in charge of managing the connection to
 * actively receive incoming requests and send outgoing responses. This thread
 * lasts as long as the connection is alive.
 */
public class GameClient extends Thread {

    private GameServer server; // References GameServer instance
    private Socket mySocket; // Socket being used for this client
    private InputStream inputStream;
    private OutputStream outputStream; // For use with outgoing responses
    private DataInputStream dataInputStream; // For use with incoming requests
    private DataInputStream dataInput;
    //private Player player;
    private boolean isPlaying;
    private Queue<GameResponse> updates; // Temporarily store responses for client
    private UserModel um ;
    Database db;
    String uname;
    /**
     * Holds the main loop that processes incoming requests by first identifying
     * its type, then interpret the following data in each determined request
     * class. Queued up responses created from each request class will be sent
     * after the request is finished processing.
     * 
     * The loop exits whenever the isPlaying flag is set to false. One of these
     * occurrences is triggered by a timeout. A timeout occurs whenever no
     * activity is picked up from the client such as being disconnected.
     */
    @Override
    public void run() {
        isPlaying = true;
        long lastActivity = System.currentTimeMillis();
        short requestCode = -1;
        boolean flag = false;
        while (isPlaying) {
            try {
                // Extract the size of the package from the data stream
                int requestLength = DataReader.readShort(dataInputStream);
              
                if (requestLength > 0) {
                	 System.out.println(requestLength+"hello");
                    lastActivity = System.currentTimeMillis();
                    // Separate the remaining package from the data stream
                    byte[] buffer = new byte[requestLength];
                    inputStream.read(buffer, 0, requestLength);
                    dataInput = new DataInputStream(new ByteArrayInputStream(buffer));
                    // Extract the request code number
                    requestCode = DataReader.readShort(dataInput);
                    System.out.println("rc :"+requestCode);
                    // Determine the type of request
                    GameRequest request = GameRequestTable.get(requestCode);
                    // If the request exists, process like following:
                    if (request != null) {
                        request.setGameClient(this);
                        
                        //Chintan : Populate the user list and character list;
                        /*if(requestCode == 101)
                        {
                        	
                        	uname= DataReader.readString(dataInput);
                        	RequestLogin.username = uname;
                        	ArrayList<CharacterModel> ch = db.getCharacterfromUser(uname);
                        	chlist = ch;
                        	UserModel um = db.getUserbyUsername(uname); 
                        	um.setCharlist(chlist);
                        	userlist.add(um);
                        	System.out.println("-----------");
                        	for(int i=0;i<userlist.size();i++)
                        	{
                        		//for(int j=0;j<userlist.get(i).getCharlist().size();j++)
                        		//{
                        			System.out.println(userlist.get(i).getUsername());
                        		//}
                        		
                        	}
                        	System.out.println("-----------");
                        }*/
                        
                        // Pass input stream to the request object
                        request.setDataInputStream(dataInput);
                        // Parse the input stream
                        request.parse();
                        // Interpret the data
                  
                        request.doBusiness();
                       
                        // Retrieve any responses created by the request object
                        for (byte response : request.respond()) {
                            // Transform the response into bytes and pass it into the output stream
                        	
                            outputStream.write(response);
                        }
                        
                    }
                } else {
                    // If there was no activity for the last moments, exit loop
                    if ((System.currentTimeMillis() - lastActivity) / 1000 >= Constants.TIMEOUT_SECONDS) {
                        isPlaying = false;
                    }
                }
            } catch (Exception e) {
                System.err.println("Request [" + requestCode + "] Error:");
                System.err.println(e.getMessage());
                System.err.println("---");
                e.printStackTrace();
            }
        }

        System.out.println(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
        System.out.println("The client stops playing.");
        server.removeActivePlayer(um.getUserid());
        

        /*if (player != null) {
            try {
                long seconds = (System.currentTimeMillis() - player.getLastSaved()) / 1000;
                player.setPlayTime(player.getPlayTime() + seconds);

                PlayerDAO.updateLogout(player.getID(), player.getPlayTime());
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

            GameServer.getInstance().removeActivePlayer(player.getID());
        }*/

        // Remove this GameClient from the server
        server.deletePlayerThreadOutOfActiveThreads(this.getId());
    }
}
