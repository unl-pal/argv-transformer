package dungeonDigger.entities;

import java.util.HashMap;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import dungeonDigger.Enums.CreatureStat;
import dungeonDigger.entities.templates.TypeTemplate;


public abstract class Agent extends GameObject {
	protected String name;
	transient protected Ability queuedAbility;
	private SpriteSheet spriteSheet;
	private TypeTemplate typeTemplate = null;
	private HashMap<CreatureStat, Integer> stats = new HashMap<>();
	private HashMap<CreatureStat, Integer> baseStats = new HashMap<>();
	

}
