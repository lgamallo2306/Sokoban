package sokoban.model.entity.box;

import sokoban.dto.EntityType;
import sokoban.model.Board;
import sokoban.model.Position;

public class NormalBox extends Box {

    public NormalBox(Position position) {
        super(position);
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void onPushed(Board board) {
    }

    @Override
    public EntityType getType() {
        return EntityType.NORMAL_BOX;
    }
}
