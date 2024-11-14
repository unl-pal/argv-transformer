package dungeonDigger.entities.templates;

import dungeonDigger.Enums.CreatureStat;
import dungeonDigger.Tools.References;
import dungeonDigger.entities.Agent;
import dungeonDigger.entities.NetworkPlayer;

public class ZombieTemplate extends TypeTemplate {
	public static int ZOMBIE_INT = 3;
	public static int ZOMBIE_WIS = 3;
	public static int ZOMBIE_CHA = 3;
	// Increment time period, 1s base per tick
	private int hungerTickTime = 1000;	
	// Time since last tick
	private int hungerTimer = 0;
	// Amount of hunger to gain per time period
	private int hungerTickAmount = 1;
	// Max hunger is 1000 == 100%
	private int hunger = 0;
}
