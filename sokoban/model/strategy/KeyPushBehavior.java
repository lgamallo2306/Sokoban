package sokoban.model.strategy;

import sokoban.model.Board;
import sokoban.model.entity.BoardEntity;

public class KeyPushBehavior implements PushBehavior {

    @Override
    public boolean canBePushed() { return true; }

    @Override
    public void onPushed(Board board, BoardEntity box) {
        if (board.hasLockAt(box.getPosition())) {
            board.openAllGates();
        }
    }
}
