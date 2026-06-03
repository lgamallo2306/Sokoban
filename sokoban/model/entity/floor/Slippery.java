package sokoban.model.entity.floor;

import sokoban.model.Position;
import sokoban.model.strategy.SlipperyLandBehavior;

public class Slippery extends Floor {

    public Slippery(Position position) {
        super(position, new SlipperyLandBehavior());
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean propagatesSlide() {
        return true;
    }
}
