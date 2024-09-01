package characters.abilities;

/**
 * Each {@link characters.abilities.Ability} has an associated AbilityType. This defines the properties that will be
 * associated with the ability. For example, a "Melee Attack", a "Spell", or a "Song" may have a unique set of
 * {@link characters.abilities.AbilityProperty}, but are all abilities that a player may act on.
 *
 * When an ability is used, all the properties of the ability are taken into account with the associated
 * {@link rules.actions.Action}
 */
public interface AbilityType {
}
