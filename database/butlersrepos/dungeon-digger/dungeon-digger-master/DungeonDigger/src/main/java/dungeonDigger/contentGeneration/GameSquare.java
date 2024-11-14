package dungeonDigger.contentGeneration;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import dungeonDigger.Tools.References;
import dungeonDigger.entities.GameObject;
import dungeonDigger.gameFlow.DungeonDigger;

public class GameSquare extends GameObject {
	private char			tileLetter	= 'W';
	private int				row, col;
	transient private Room	belongsTo	= null;
}
