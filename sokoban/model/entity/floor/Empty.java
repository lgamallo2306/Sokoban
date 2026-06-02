package sokoban.model.entity.floor;

import sokoban.dto.EntityType;
import sokoban.model.Position;

public class Empty extends Floor {

    public Empty(Position position) {
        super(position);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public EntityType getType() {
        return EntityType.EMPTY;
    }
}
