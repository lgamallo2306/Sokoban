package sokoban.model.entity.box;

import sokoban.model.Position;
import sokoban.model.strategy.FragilePushBehavior;

public class FragileBox extends Box {

    private static final int DEFAULT_RESISTANCE = 1;

    private final FragilePushBehavior pushBehavior;

    public FragileBox(Position position) {
        this(position, DEFAULT_RESISTANCE);
    }

    public FragileBox(Position position, int resistance) {
        this(position, new FragilePushBehavior(resistance));
    }

    private FragileBox(Position position, FragilePushBehavior behavior) {
        super(position, behavior);
        this.pushBehavior = behavior;
    }

    public int getResistance() {
        return pushBehavior.getResistance();
    }
}
