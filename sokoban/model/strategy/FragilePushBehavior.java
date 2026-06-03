package sokoban.model.strategy;

import sokoban.model.Board;
import sokoban.model.entity.BoardEntity;

public class FragilePushBehavior implements PushBehavior {

    private int resistance;

    public FragilePushBehavior(int resistance) {
        this.resistance = resistance;
    }

    @Override
    public boolean canBePushed() { return resistance > 0; }

    @Override
    public void onPushed(Board board, BoardEntity box) {
        if (resistance > 0) {
            resistance--;
        }
        if (resistance <= 0) {
            board.removeEntity(box);
        }
    }

    public int getResistance() { return resistance; }
}
