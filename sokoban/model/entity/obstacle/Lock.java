package sokoban.model.entity.obstacle;

import sokoban.dto.EntityType;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;
import sokoban.model.entity.Sokoban;
import sokoban.model.entity.box.KeyBox;

public class Lock extends BoardEntity {

    public Lock(Position position) {
        super(position);
    }

    @Override
    public EntityType getType() {
        return EntityType.LOCK;
    }

    @Override
    public boolean isWalkableBy(BoardEntity actor) {
        return actor instanceof Sokoban;
    }

    @Override
    public boolean isBoxTraversable(BoardEntity box) {
        return box instanceof KeyBox;
    }
}
