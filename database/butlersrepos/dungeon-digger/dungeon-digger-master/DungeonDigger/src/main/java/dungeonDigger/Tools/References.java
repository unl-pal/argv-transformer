package dungeonDigger.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Image;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

import dungeonDigger.Enums.GameState;
import dungeonDigger.collisions.QuadCollisionEngine;
import dungeonDigger.contentGeneration.DungeonGenerator;
import dungeonDigger.contentGeneration.GameSquare;
import dungeonDigger.entities.Ability;
import dungeonDigger.entities.AbilityFactory;
import dungeonDigger.entities.GameObject;
import dungeonDigger.entities.Mob;
import dungeonDigger.entities.MobFactory;
import dungeonDigger.entities.NetworkPlayer;
import dungeonDigger.gameFlow.DungeonDigger;
import dungeonDigger.gameFlow.MultiplayerDungeon;
import dungeonDigger.network.ConnectionState;
import dungeonDigger.network.Network.ChatPacket;
import dungeonDigger.network.Network.TextPacket;

public class References {
	public static NetworkPlayer myCharacter;
	public static Server SERVER = new Server();
	public static Client CLIENT = new Client();	
	public static GameState CHOSEN_GAME_STATE;
	public static String ACCOUNT_NAME;
	public static String IP_CONNECT;
	public static ConnectionState STATE;
	public static ArrayList<NetworkPlayer> PLAYER_LIST = new ArrayList<NetworkPlayer>();
	public static HashMap<String, Image> IMAGES = new HashMap<String, Image>();
	public static ArrayList<TextPacket> TEXTS = new ArrayList<TextPacket>();
	public static LinkedList<ChatPacket> CHATS = new LinkedList<ChatPacket>();
	public static HashMap<String, NetworkPlayer> CHARACTERBANK = new HashMap<String, NetworkPlayer>();
	public static HashSet<String> ACTIVESESSIONNAMES = new HashSet<String>();
	public static HashMap<Integer, String> KEY_BINDINGS = new HashMap<Integer, String>();
	public static HashMap<String, String> SLOT_BINDINGS = new HashMap<String, String>();
	public static HashMap<String, Ability> ABILITY_TEMPLATES = new HashMap<String, Ability>();
	public static HashMap<String, Mob> MOB_TEMPLATES = new HashMap<String, Mob>();
	public static Vector<Ability> ACTIVE_ABILITIES = new Vector<>();
	public static AbilityFactory ABILITY_FACTORY = new AbilityFactory();
	public static MobFactory MOB_FACTORY = new MobFactory();
	public static QuadCollisionEngine QUAD_COLLISION_MANIFOLD;
	public static Logger log = Logger.getLogger("REFERENCES");
	public static DungeonGenerator CLIENT_VIEW;
	public static boolean PAUSED = false, DEBUG_MODE = false;
	
	static {
		log.setLevel(Level.ALL);
	}
}
