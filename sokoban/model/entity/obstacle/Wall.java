package sokoban.model.entity.obstacle;

import sokoban.dto.EntityType;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;

public class Wall extends BoardEntity {

    public Wall(Position position) {
        super(position);
    }

    @Override
    public EntityType getType() {
        return EntityType.WALL;
    }
}
