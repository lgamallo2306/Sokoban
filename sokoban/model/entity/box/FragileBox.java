package sokoban.model.entity.box;

import sokoban.dto.EntityType;
import sokoban.model.Board;
import sokoban.model.Position;

public class FragileBox extends Box {

    private static final int DEFAULT_RESISTANCE = 1;

    private int resistance;

    public FragileBox(Position position) {
        this(position, DEFAULT_RESISTANCE);
    }

    public FragileBox(Position position, int resistance) {
        super(position);
        this.resistance = resistance;
    }

    @Override
    public boolean canBePushed() {
        return resistance > 0;
    }

    @Override
    public void onPushed(Board board) {
        if (resistance > 0) {
            resistance--;
        }
        if (resistance <= 0) {
            board.removeEntity(this);
        }
    }

    public int getResistance() {
        return resistance;
    }

    @Override
    public EntityType getType() {
        return EntityType.FRAGILE_BOX;
    }
}
