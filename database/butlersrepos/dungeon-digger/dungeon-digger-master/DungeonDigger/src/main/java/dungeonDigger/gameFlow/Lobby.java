package dungeonDigger.gameFlow;

import java.awt.Font;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonDigger.Enums.GameState;
import dungeonDigger.Tools.Constants;
import dungeonDigger.Tools.References;
import dungeonDigger.entities.NetworkPlayer;
import dungeonDigger.network.ConnectionState;
import dungeonDigger.network.Network;
import dungeonDigger.network.Network.ChatPacket;
import dungeonDigger.network.Network.GameJoinPacket;
import dungeonDigger.network.Network.LoginRequest;
import dungeonDigger.network.Network.SignOff;
import dungeonDigger.network.Network.TextPacket;

public class Lobby extends BasicGameState implements MouseListener{		
	private boolean isServer;
	private Logger logger = Logger.getLogger("DungeonDigger.Lobby");
		
	private TextField inputBox, startButton;
}
