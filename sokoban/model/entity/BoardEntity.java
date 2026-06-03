package sokoban.model.entity;

import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.Position;

public abstract class BoardEntity {

    protected Position position;

    protected BoardEntity(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isWalkableBy(BoardEntity actor)   { return false; }
    public boolean isBoxTraversable(BoardEntity box) { return false; }

    public boolean isFloor()            { return false; }
    public boolean isBox()              { return false; }
    public boolean isSokoban()          { return false; }
    public boolean isLock()             { return false; }

    public boolean canWalkThroughLock() { return false; }
    public boolean canTraverseLock()    { return false; }
    public void    openGate()           { }

    public boolean canBePushed()                                           { return false; }
    public void    onPushed(Board board)                                   { }

    public boolean isGoal()                                                { return false; }
    public boolean propagatesSlide()                                       { return false; }
    public void    onBoxLanded(BoardEntity box, Direction dir, Board board) { }
}
