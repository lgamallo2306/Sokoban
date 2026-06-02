package sokoban.model.entity.box;

import sokoban.dto.EntityType;
import sokoban.model.Board;
import sokoban.model.Position;

public class KeyBox extends Box {

    public KeyBox(Position position) {
        super(position);
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void onPushed(Board board) {
        if (board.hasLockAt(getPosition())) {
            board.openAllGates();
        }
    }

    @Override
    public EntityType getType() {
        return EntityType.KEY_BOX;
    }
}
