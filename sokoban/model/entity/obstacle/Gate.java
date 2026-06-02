package sokoban.model.entity.obstacle;

import sokoban.dto.EntityType;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;

public class Gate extends BoardEntity {

    private boolean isOpen;

    public Gate(Position position) {
        this(position, false);
    }

    public Gate(Position position, boolean isOpen) {
        super(position);
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        this.isOpen = true;
    }

    public void close() {
        this.isOpen = false;
    }

    @Override
    public EntityType getType() {
        return isOpen ? EntityType.GATE_OPEN : EntityType.GATE_CLOSED;
    }

    @Override
    public boolean isWalkableBy(BoardEntity actor) {
        return isOpen;
    }

    @Override
    public boolean isBoxTraversable(BoardEntity box) {
        return isOpen;
    }
}
