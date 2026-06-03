package sokoban.model.strategy;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;

public class SlipperyLandBehavior implements LandBehavior {

    @Override
    public void onBoxLanded(BoardEntity box, Direction dir, Board board) {
        while (true) {
            Position next = box.getPosition().translate(dir);
            if (!board.isInside(next)) return;
            BoardEntity occupant = board.getEntityAt(next);
            if (!occupant.isBoxTraversable(box)) return;
            box.setPosition(next);
            box.onPushed(board);
            BoardEntity floor = board.getFloorAt(next);
            if (floor == null || !floor.propagatesSlide()) return;
        }
    }
}
