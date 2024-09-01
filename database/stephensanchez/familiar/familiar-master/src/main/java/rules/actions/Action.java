package rules.actions;

/**
 * Actions define explicit choices made by the Game Master or Players. These are similar to {@link rules.events.Event}s,
 * in that they may modify {@link characters.attributes.Attribute}s, but actions never occur automatically.
 *
 * The implications of an Actions are not always known. A player may choose to hit a monster, but they could miss. An
 * action will take into account {@link characters.attributes.Attribute}s, and external data resources (such as a die
 * roll) to draw a conclusion, which is recorded internally as a {@link rules.actions.ActionTransaction}, and returned
 * to from the API as an {@link rules.actions.ActionImplication}.
 */
public interface Action {
}
