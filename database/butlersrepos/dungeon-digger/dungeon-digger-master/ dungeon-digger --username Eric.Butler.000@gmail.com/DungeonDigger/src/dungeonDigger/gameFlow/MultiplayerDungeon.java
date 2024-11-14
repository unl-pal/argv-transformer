package dungeonDigger.gameFlow;

import java.util.logging.Logger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryonet.Connection;

import dungeonDigger.contentGeneration.DungeonGenerator;
import dungeonDigger.network.ConnectionState;
import dungeonDigger.network.Network;

public class MultiplayerDungeon extends DungeonDiggerState {
	private Vector2f startPos;
	// Used for dungeon data of both, aside: "CLIENT" = viewable interface
	public static DungeonGenerator CLIENT_VIEW;
	private Logger logger = Logger.getLogger("DungeonDigger.MultiplayerDungeon");
}
