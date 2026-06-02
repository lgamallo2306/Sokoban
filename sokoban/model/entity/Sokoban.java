package sokoban.model.entity;

import sokoban.dto.EntityType;
import sokoban.model.Position;

public class Sokoban extends BoardEntity {

    public Sokoban(Position position) {
        super(position);
    }

    @Override
    public EntityType getType() {
        return EntityType.SOKOBAN;
    }
}
