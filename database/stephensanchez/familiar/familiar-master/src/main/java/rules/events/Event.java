package rules.events;

/**
 * Events are based on {@link rules.actions.Action}s or {@link characters.attributes.Attribute} updates. For example,
 * if a character's 'Experience Points' attribute hits '3000', an event may indicate that the
 * {@link java.lang.Character} gained a level, and the 'Level' attribute is increased. Increasing the 'Level' attribute
 * may fire additional events and modify additional {@link characters.attributes.Attribute}s.
 *
 * An {@link rules.actions.Action} may also cause an event to be fired. For example, if a player hits a monster, there
 * may be a 'on hit' event, which modifies the attack damage {@link characters.attributes.Attribute}
 */
public interface Event {
}
