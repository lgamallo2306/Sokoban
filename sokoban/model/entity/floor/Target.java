package sokoban.model.entity.floor;

import sokoban.model.Position;
import sokoban.model.strategy.DefaultLandBehavior;

public class Target extends Floor {

    public Target(Position position) {
        super(position, new DefaultLandBehavior());
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean isGoal() {
        return true;
    }
}
