package sokoban.model.entity.box;

import sokoban.model.Board;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;

public abstract class Box extends BoardEntity {

    protected Box(Position position) {
        super(position);
    }

    public abstract boolean canBePushed();

    public abstract void onPushed(Board board);
}
