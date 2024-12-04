package dungeonDigger.entities;

import java.util.LinkedList;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import dungeonDigger.Enums.Direction;
import dungeonDigger.Tools.References;
import dungeonDigger.gameFlow.DungeonDigger;
import dungeonDigger.gameFlow.MultiplayerDungeon;
import dungeonDigger.network.Network;
import dungeonDigger.network.Network.PlayerMovementUpdate;

public class NetworkPlayer extends Agent implements KeyListener, MouseListener {
	/* Actual stored fields of the Player */			
	/** Image/avatar short filename our player uses **/
	private String iconName = "engy";						
	/** If 0: die **/
	private int hitPoints = 20;								
	/** How many pixels our character can move per step **/
	private int speed = 6;		
	/** Used for local rendering while we query server to validate movement **/
	transient private float proposedPlayerX, proposedPlayerY;	
	transient private double reload = 500;
	/** Tracks time passes until we can fire **/
	transient private double reloadTimer = 0;				
	/** Tells if we're facing left **/
	transient private boolean flippedLeft, pendingValidation;					
	transient Logger logger = Logger.getLogger("NetworkPlayer");
	transient LinkedList<Vector2f> movementList = new LinkedList<Vector2f>();
	transient boolean movingUp, movingDown, movingLeft, movingRight;
	transient Input inputs = null;
	transient Vector2f currentClick;
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		References.log.info("Mouse clicked at: " + x + ", " + y);
		if( this.getQueuedAbility() != null  && this.getQueuedAbility().isWaitingForClick() ) {
			this.getQueuedAbility().setEndPoint(References.myCharacter.getPosition().x - 320 + x,
												References.myCharacter.getPosition().y - 320 + y);
			this.getQueuedAbility().setActive(true);
			this.getQueuedAbility().setWaitingForClick(false);
			this.setQueuedAbility(null);
		}
	}
}
