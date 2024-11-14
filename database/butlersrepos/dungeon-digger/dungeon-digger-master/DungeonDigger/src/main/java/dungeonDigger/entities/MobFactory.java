package dungeonDigger.entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import dungeonDigger.Tools.References;
import dungeonDigger.collisions.QuadCollisionEngine;
import dungeonDigger.contentGeneration.DungeonGenerator;

public class MobFactory {
	private static HashMap<String, Vector<Mob>> theCryoTubes = new HashMap<>();
	private static HashMap<String, Vector<Mob>> roamingMobs = new HashMap<>();
	// TODO: Find good numbers of clones for each enemy to pre-load, ie more zombies than demons?
	

}
