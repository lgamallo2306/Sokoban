package sokoban.model.entity;

import sokoban.model.Position;

public class Sokoban extends BoardEntity {

    public Sokoban(Position position) {
        super(position);
    }

    @Override
    public boolean isSokoban() { return true; }

    @Override
    public boolean canWalkThroughLock() { return true; }
}
