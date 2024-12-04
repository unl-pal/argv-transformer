package dungeonDigger.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import dungeonDigger.Tools.Constants;
import dungeonDigger.Tools.References;
import dungeonDigger.collisions.QuadCollisionEngine;

public abstract class GameObject {
	/** Actual pixel measurements **/
	private Vector2f			position		= new Vector2f();
	private Rectangle			collisionBox	= new Rectangle(0, 0, 0, 0);
	private QuadCollisionEngine	parentNode		= null;
}
