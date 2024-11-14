package dungeonDigger.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import dungeonDigger.Enums.GameState;
import dungeonDigger.Tools.References;
import dungeonDigger.contentGeneration.GameSquare;
import dungeonDigger.entities.Agent;
import dungeonDigger.entities.GameObject;
import dungeonDigger.entities.NetworkPlayer;
import dungeonDigger.gameFlow.MultiplayerDungeon;

public class Network {
	static public final int port = 54555;
	//private Logger logger = Logger.getLogger("DungeonDigger.Network");
	
	//////////////////////////////////
	// Listeners that the game uses //
	//////////////////////////////////
	static public class ServerListener extends Listener {
		Logger logger = Logger.getLogger("ServerListener");
	}
	static public class ClientListener extends Listener {
		Logger logger = Logger.getLogger("ClientListener");
	}
	
	//////////////////////////////////////
	// Traffic, packets, responses, etc //
	//////////////////////////////////////
	static abstract class Response {
		public boolean response;
	}
	static public class ChatPacket {
		public String text;
	}
	static public class GameJoinPacket {
		public int gameStateId;
	}
	static public class GameStartPacket {
		public int x, y;
	}
	static public class LoginRequest {
		public String ipAddress, account, password;
	}
	static public class LoginResponse extends Response{
	}
	static public class SignOff {
		public String ipAddress, account, password;
	}
	static public class TextPacket {
		public String text;
		// Duration, 0 = indefinite
		public int x, y, durationInMilliseconds, passedTime = 0;
	}
	static public class PlayerInfoPacket {
		public NetworkPlayer player;
	}
	static public class PlayerListPacket {
		public ArrayList<NetworkPlayer> players;
		
	}
	static public class PlayerMovementUpdate {
		public String player;
		public float x, y;
	}
	static public class PlayerMovementRequest {
		public String player;
		public float x, y;
	}
	static public class PlayerMovementResponse extends Response {
	}
	static public class TilesResponse {
		public List<GameSquare> tiles;
	}
	static public class TileResponse {
		public GameSquare tile;
		public int row, col;
	}
	static public class TilesRequest {
		public int[] list;
	}
	static public class WholeMapPacket {
		public GameSquare[][] dungeon;
		public ArrayList<NetworkPlayer> players;
	}
}
