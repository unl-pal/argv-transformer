package characters;

/**
 * A Player, NPC, or monster in your world. Each character is backed by a {@link CharacterType}, which acts as the
 * character sheet guiding the required {@link characters.attributes.Attribute}s to define this character.
 *
 * The character is the main defining object in the Familiar API. From a Character any number of
 * {@link rules.actions.Action}s and {@link rules.events.Event}s may occur, based on the
 * {@link characters.abilities.Ability}s, {@link characters.attributes.Attribute}s, and {@link items.Item}
 * associated with that character.
 */
public interface Character {
}
