package sokoban.model;

public class MoveResult {

    private final boolean moved;
    private final boolean pushed;

    public MoveResult(boolean moved, boolean pushed) {
        this.moved = moved;
        this.pushed = pushed;
    }

    public boolean isMoved() {
        return moved;
    }

    public boolean isPushed() {
        return pushed;
    }

    public static MoveResult blocked() {
        return new MoveResult(false, false);
    }
}
