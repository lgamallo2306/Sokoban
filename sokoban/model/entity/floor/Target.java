package sokoban.model.entity.floor;

import sokoban.dto.EntityType;
import sokoban.model.Position;

public class Target extends Floor {

    public Target(Position position) {
        super(position);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public EntityType getType() {
        return EntityType.TARGET;
    }

    @Override
    public boolean isGoal() {
        return true;
    }
}
