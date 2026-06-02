package sokoban.model;

import sokoban.model.entity.BoardEntity;
import sokoban.model.entity.Sokoban;
import sokoban.model.entity.box.Box;
import sokoban.model.entity.floor.Floor;

public class GameEngine {

    private final Board board;
    private final Sokoban sokoban;

    public GameEngine(Board board, Sokoban sokoban) {
        this.board = board;
        this.sokoban = sokoban;
    }

    public Board getBoard() {
        return board;
    }

    public Sokoban getSokoban() {
        return sokoban;
    }

    public MoveResult move(Direction dir) {
        Position newPos = sokoban.getPosition().translate(dir);
        if (!board.isInside(newPos)) {
            return MoveResult.blocked();
        }

        BoardEntity target = board.getEntityAt(newPos);

        boolean pushed = false;
        if (target instanceof Box) {
            Box box = (Box) target;
            if (!tryPushBox(box, dir)) {
                return MoveResult.blocked();
            }
            pushed = true;
        } else if (!target.isWalkableBy(sokoban)) {
            return MoveResult.blocked();
        }

        sokoban.setPosition(newPos);
        return new MoveResult(true, pushed);
    }

    private boolean tryPushBox(Box box, Direction dir) {
        if (!box.canBePushed()) {
            return false;
        }
        Position destination = box.getPosition().translate(dir);
        if (!board.isInside(destination)) {
            return false;
        }
        BoardEntity occupant = board.getEntityAt(destination);
        if (!occupant.isBoxTraversable(box)) {
            return false;
        }

        box.setPosition(destination);
        box.onPushed(board);

        Floor under = board.getFloorAt(box.getPosition());
        if (under != null) {
            under.onBoxLanded(box, dir, board);
        }
        return true;
    }

    public boolean checkVictory() {
        for (Box box : board.getBoxes()) {
            Floor floor = board.getFloorAt(box.getPosition());
            if (floor == null || !floor.isGoal()) {
                return false;
            }
        }
        return true;
    }
}
