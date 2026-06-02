package sokoban.model.entity.floor;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;
import sokoban.model.entity.box.Box;

public abstract class Floor extends BoardEntity {

    protected Floor(Position position) {
        super(position);
    }

    public abstract boolean isWalkable();

    @Override
    public boolean isWalkableBy(BoardEntity actor) {
        return isWalkable();
    }

    @Override
    public boolean isBoxTraversable(BoardEntity box) {
        return isWalkable();
    }

    public void onBoxLanded(Box box, Direction dir, Board board) {
    }

    public boolean propagatesSlide() {
        return false;
    }

    public boolean isGoal() {
        return false;
    }
}
