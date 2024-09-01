package mtgdeckbuilder.data;

public enum Function {
    m("contains"),
    eq("equal to"),
    not("not equal"),
    gt("greater than"),
    gte("greater than or equal to"),
    lt("less than"),
    lte("less than or equal to");

    private final String displayableName;

    private Function(String displayableName) {
        this.displayableName = displayableName;
    }

    @Override
    public String toString() {
        return displayableName;
    }

}
