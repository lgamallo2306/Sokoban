package sokoban.model.entity.floor;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;
import sokoban.model.strategy.LandBehavior;

public abstract class Floor extends BoardEntity {

    private final LandBehavior landBehavior;

    protected Floor(Position position, LandBehavior landBehavior) {
        super(position);
        this.landBehavior = landBehavior;
    }

    @Override
    public boolean isFloor() { return true; }

    public abstract boolean isWalkable();

    @Override
    public boolean isWalkableBy(BoardEntity actor)   { return isWalkable(); }

    @Override
    public boolean isBoxTraversable(BoardEntity box) { return isWalkable(); }

    @Override
    public void onBoxLanded(BoardEntity box, Direction dir, Board board) {
        landBehavior.onBoxLanded(box, dir, board);
    }

    @Override
    public boolean propagatesSlide() { return false; }

    @Override
    public boolean isGoal() { return false; }
}
