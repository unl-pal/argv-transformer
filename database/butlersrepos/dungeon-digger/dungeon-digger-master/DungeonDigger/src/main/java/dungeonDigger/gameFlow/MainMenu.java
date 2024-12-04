package dungeonDigger.gameFlow;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import dungeonDigger.Enums.GameState;
import dungeonDigger.Tools.References;
import dungeonDigger.entities.NetworkPlayer;
import dungeonDigger.network.ConnectionState;

public class MainMenu extends BasicGameState {
	TextField tf, iptf;
	boolean listening, connecting, triggered, go;
	String accountName, ip;
}
