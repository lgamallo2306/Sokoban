package sokoban.dto;

public enum EntityType {
    WALL('#', 3),
    EMPTY(' ', 1),
    SOKOBAN('@', 5),
    NORMAL_BOX('$', 4),
    FRAGILE_BOX('F', 4),
    KEY_BOX('K', 4),
    TARGET('.', 2),
    SLIPPERY('~', 2),
    LOCK('L', 3),
    GATE_OPEN('O', 3),
    GATE_CLOSED('X', 3);

    private final char symbol;
    private final int renderPriority;

    EntityType(char symbol, int renderPriority) {
        this.symbol = symbol;
        this.renderPriority = renderPriority;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getRenderPriority() {
        return renderPriority;
    }
}
