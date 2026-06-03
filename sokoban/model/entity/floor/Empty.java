package sokoban.model.entity.floor;

import sokoban.model.Position;
import sokoban.model.strategy.DefaultLandBehavior;

public class Empty extends Floor {

    public Empty(Position position) {
        super(position, new DefaultLandBehavior());
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
