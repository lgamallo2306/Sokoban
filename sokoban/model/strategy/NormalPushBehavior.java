package sokoban.model.strategy;

import sokoban.model.Board;
import sokoban.model.entity.BoardEntity;

public class NormalPushBehavior implements PushBehavior {

    @Override
    public boolean canBePushed() { return true; }

    @Override
    public void onPushed(Board board, BoardEntity box) { }
}
