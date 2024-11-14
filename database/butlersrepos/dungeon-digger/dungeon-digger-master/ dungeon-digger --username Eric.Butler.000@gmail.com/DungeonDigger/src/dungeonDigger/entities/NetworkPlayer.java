package dungeonDigger.entities;

import java.util.LinkedList;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import dungeonDigger.enums.Direction;
import dungeonDigger.gameFlow.DungeonDigger;
import dungeonDigger.gameFlow.MultiplayerDungeon;
import dungeonDigger.network.Network;
import dungeonDigger.network.Network.PlayerMovementUpdate;

public class NetworkPlayer {
	/* Actual stored fields of the Player */
	private String name;
	// Actual pixel measurement
	private int playerXCoord = 500, playerYCoord = 500;				
	// Image/avatar our player uses
	private String iconName = "dwarf1";						
	// If 0: die
	private int hitPoints = 20;								
	// How many pixels our character can move per step
	private int speed = 3;		
	
	// Used for local rendering while we query server to validate movement
	transient private int proposedPlayerX, proposedPlayerY;	
	transient private double reload = 500;
	// Tracks time passes until we can fire
	transient private double reloadTimer = 0;				
	// Tells if we're facing left
	transient private boolean flippedLeft, pendingValidation;					
	transient private Image icon;
	transient Logger logger = Logger.getLogger("NetworkPlayer");
	transient LinkedList<Point> movementList = new LinkedList<Point>();
}
