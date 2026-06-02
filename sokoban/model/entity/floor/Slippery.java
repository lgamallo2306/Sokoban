package sokoban.model.entity.floor;

import sokoban.dto.EntityType;
import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;
import sokoban.model.entity.box.Box;

public class Slippery extends Floor {

    public Slippery(Position position) {
        super(position);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public EntityType getType() {
        return EntityType.SLIPPERY;
    }

    @Override
    public boolean propagatesSlide() {
        return true;
    }

    @Override
    public void onBoxLanded(Box box, Direction dir, Board board) {
        while (true) {
            Position next = box.getPosition().translate(dir);
            if (!board.isInside(next)) return;
            BoardEntity occupant = board.getEntityAt(next);
            if (!occupant.isBoxTraversable(box)) return;
            box.setPosition(next);
            box.onPushed(board);
            Floor floor = board.getFloorAt(next);
            if (floor == null || !floor.propagatesSlide()) return;
        }
    }
}
