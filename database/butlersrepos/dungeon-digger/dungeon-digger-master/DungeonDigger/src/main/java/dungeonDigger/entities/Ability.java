package dungeonDigger.entities;

import java.util.ArrayList;
import java.util.logging.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import dungeonDigger.Enums.AbilityDeliveryMethod;
import dungeonDigger.Enums.Direction;
import dungeonDigger.Tools.References;
import dungeonDigger.Tools.Toolbox;
import dungeonDigger.collisions.QuadCollisionEngine;
import dungeonDigger.gameFlow.MultiplayerDungeon;
/**
 * This class should handle all abilities, their animations, movement, collisions, and life-cycle.
 * Abilities should include all spells, skills, etc as well.
 * @author erbutler
 */
public class Ability extends GameObject {
	public static Ability EMPTY_ABILITY = new Ability("Empty") {
		@Override
		public void setActive(boolean value) { }
		@Override
		public void setChanneling(boolean value) { }
	};
	private String name;
	private boolean active, damaging, channeling, friendly, mouse, inited, waitForClick, collided = false;
	private SpriteSheet spriteSheet, hitFrames;
	private Animation animation, secondaryAnimation;
	private Agent owner;
	private int speed, step = 0;
	private Vector2f startPoint = new Vector2f(), middlePoint, endPoint, collisionPoint = null;
	private Float lineOfTravel = null;
	private AbilityDeliveryMethod adm;
	transient double distance = -1, intervals = 0;
	
	public void calculateMovement() {
		if( step > intervals ) {
			References.log.info("Steps > Intervals, stopping");
			distance = -1;
			step = 0;
			animation.stop();
			collided = true;
			return;
		}
		// This means we are just starting
		if( distance == -1 ) { 
			References.log.info("Setting up pathing items");
			distance = Toolbox.distanceBetween(startPoint, endPoint); 
			intervals = distance / speed;
			References.log.info("Distance: " + distance + " Intervals: " + intervals);
			step = 0;
		}
		int newX = (int)(startPoint.x + (endPoint.x - startPoint.x) * (step / intervals)); 
		int newY = (int)(startPoint.y + (endPoint.y - startPoint.y) * (step / intervals)); 

		//Check terrain
		/*Line path = new Line(this.getPosition().copy(), new Vector2f(newX, newY));
		Direction dir = Toolbox.getCardinalDirection(path);
		collisionPoint = null;
		for( int row = 0; row != dir.adjY(); row += dir.adjY() ) {
			for( int col = 0; col != dir.adjX(); col += dir.adjX() ) {
				if( !MultiplayerDungeon.CLIENT_VIEW.dungeon[row][col].isPassable() ) {
					if( path.intersects(MultiplayerDungeon.CLIENT_VIEW.dungeon[row][col].getCollisionBox() ) ) {
						collisionPoint = Toolbox.lineIntersectsRectangle(path, MultiplayerDungeon.CLIENT_VIEW.dungeon[row][col].getCollisionBox());
						System.out.println("HIT A WALL!");
						collided = true;
						break;
					}
				}dddddddddddd
			}
			if( collided ) { break; }
		}*/ 
		// Move
		//if( collided ) {
			//this.setPosition(this.collisionPoint.copy());
			//step = (int)Math.ceil(intervals);
		//} else {
			this.getPosition().x = newX;
			this.getPosition().y = newY;
		//}
		// Repopulate Quads
		if( !this.getParentNode().contains(this) || this.getParentNode().getChildren().size() > 0 ) {
			QuadCollisionEngine.relocate(this);
		}
		// Check collisions			
		ArrayList<GameObject> obstacles = QuadCollisionEngine.checkCollisions(this);
		if( obstacles != null ) {
			this.handleCollisions(obstacles);
		}
		
		step++;
	}

}
