package dungeonDigger.gameFlow;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class facilitates the standard of updating the myCharacter object's logic and
 * rendering the dungeon.<br/>
 * All child objects should call their <b>super.update()</b> and <b>super.render()</b> before
 * doing their specific tasks.
 * @author Eric
 *
 */
public abstract class DungeonDiggerState extends BasicGameState {
}
