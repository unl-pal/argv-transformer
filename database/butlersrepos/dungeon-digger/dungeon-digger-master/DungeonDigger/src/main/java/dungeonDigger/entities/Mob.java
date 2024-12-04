package dungeonDigger.entities;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;

import dungeonDigger.Enums.CreatureStat;
import dungeonDigger.Enums.Direction;
import dungeonDigger.Tools.References;
import dungeonDigger.Tools.Toolbox;
import dungeonDigger.collisions.QuadCollisionEngine;
import dungeonDigger.contentGeneration.DungeonGenerator;

public class Mob extends Agent {
	private SpriteSheet sprites;
	private Animation animation;
	private Vector2f destination;	
	private int currentHitPoints, maxHitPoints, speed, intelligence = 1;
	private boolean friendly = false, exists = false, inited = false;
	private transient float movementVariance = 2f;
	private transient int aggroRange = 750;
}
