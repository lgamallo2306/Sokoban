package sokoban.model.entity.box;

import sokoban.model.Position;
import sokoban.model.strategy.KeyPushBehavior;

public class KeyBox extends Box {

    public KeyBox(Position position) {
        super(position, new KeyPushBehavior());
    }

    @Override
    public boolean canTraverseLock() { return true; }
}
