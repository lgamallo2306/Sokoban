package sokoban.model.entity;

import sokoban.dto.EntityType;
import sokoban.model.Position;

public abstract class BoardEntity {

    protected Position position;

    protected BoardEntity(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract EntityType getType();

    public boolean isWalkableBy(BoardEntity actor) {
        return false;
    }

    public boolean isBoxTraversable(BoardEntity box) {
        return false;
    }
}
