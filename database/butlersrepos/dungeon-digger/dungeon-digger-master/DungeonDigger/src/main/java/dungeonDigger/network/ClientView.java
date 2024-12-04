package dungeonDigger.network;

import java.util.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.esotericsoftware.kryonet.Client;

import dungeonDigger.contentGeneration.GameSquare;
import dungeonDigger.entities.NetworkPlayer;

public class ClientView {
	private Client connection;
	private GameSquare[][] tiles;
	private Vector<NetworkPlayer> dudes;
}
