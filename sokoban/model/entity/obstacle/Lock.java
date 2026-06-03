package sokoban.model.entity.obstacle;

import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;

public class Lock extends BoardEntity {

    public Lock(Position position) {
        super(position);
    }

    @Override
    public boolean isLock() { return true; }

    @Override
    public boolean isWalkableBy(BoardEntity actor) {
        return actor.canWalkThroughLock();
    }

    @Override
    public boolean isBoxTraversable(BoardEntity box) {
        return box.canTraverseLock();
    }
}
