package sokoban.model.strategy;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.entity.BoardEntity;

public class DefaultLandBehavior implements LandBehavior {

    @Override
    public void onBoxLanded(BoardEntity box, Direction dir, Board board) { }
}
