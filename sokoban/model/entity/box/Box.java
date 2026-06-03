package sokoban.model.entity.box;

import sokoban.model.Board;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;
import sokoban.model.strategy.PushBehavior;

public abstract class Box extends BoardEntity {

    private final PushBehavior pushBehavior;

    protected Box(Position position, PushBehavior pushBehavior) {
        super(position);
        this.pushBehavior = pushBehavior;
    }

    @Override
    public boolean isBox() { return true; }

    @Override
    public boolean canBePushed() { return pushBehavior.canBePushed(); }

    @Override
    public void onPushed(Board board) { pushBehavior.onPushed(board, this); }
}
