package sokoban.model.strategy;

import sokoban.model.Board;
import sokoban.model.entity.BoardEntity;

public interface PushBehavior {
    boolean canBePushed();
    void onPushed(Board board, BoardEntity box);
}
