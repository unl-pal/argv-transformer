package mtgdeckbuilder.data;

public enum Field {
    name,
    description,
    flavor,
    color,
    manacost("mana cost"),
    convertedmanacost("converted mana cost"),
    type,
    subtype,
    power,
    toughness,
    loyalty,
    rarity,
    artist,
    setId("set id");

    private final String displayableName;

    private Field() {
        this.displayableName = this.name();
    }

    private Field(String displayableName) {
        this.displayableName = displayableName;
    }

    @Override
    public String toString() {
        return displayableName;
    }

}
