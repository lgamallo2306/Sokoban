package sokoban.model.entity.box;

import sokoban.model.Position;
import sokoban.model.strategy.NormalPushBehavior;

public class NormalBox extends Box {

    public NormalBox(Position position) {
        super(position, new NormalPushBehavior());
    }
}
