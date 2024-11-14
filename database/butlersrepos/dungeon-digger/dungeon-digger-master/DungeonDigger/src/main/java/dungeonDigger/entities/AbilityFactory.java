package dungeonDigger.entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import dungeonDigger.Tools.References;
import dungeonDigger.collisions.QuadCollisionEngine;
import dungeonDigger.contentGeneration.DungeonGenerator;
import dungeonDigger.gameFlow.DungeonDigger;

public class AbilityFactory {
	private static HashMap<String, Vector<Ability>> storedAbilities = new HashMap<>();
	private static HashMap<String, Vector<Ability>> activeAbilities = new HashMap<>();
	// TODO: Find good numbers of clones for each spell to pre-load, ie more Turrets than fireballs?
	

}
