package dungeonDigger.gameFlow;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import dungeonDigger.Tools.References;
import dungeonDigger.collisions.QuadCollisionEngine;
import dungeonDigger.contentGeneration.DungeonGenerator;
import dungeonDigger.entities.NetworkPlayer;

public class SinglePlayerDungeon extends DungeonDiggerState implements KeyListener, MouseListener {
	private double[] hallsDensity = new double[]{1d, 0.95d};
	private NetworkPlayer myPlayer;
	private HashMap<Integer, Boolean> keyToggled = new HashMap<Integer, Boolean>();
}
