package sokoban.model;

import sokoban.model.entity.BoardEntity;

public class GameEngine {

    private final Board board;
    private final BoardEntity sokoban;

    public GameEngine(Board board, BoardEntity sokoban) {
        this.board = board;
        this.sokoban = sokoban;
    }

    public Board getBoard() {
        return board;
    }

    public BoardEntity getSokoban() {
        return sokoban;
    }

    public MoveResult move(Direction dir) {
        Position newPos = sokoban.getPosition().translate(dir);
        if (!board.isInside(newPos)) {
            return MoveResult.blocked();
        }

        BoardEntity target = board.getEntityAt(newPos);

        boolean pushed = false;
        if (target.canBePushed()) {
            if (!tryPushBox(target, dir)) {
                return MoveResult.blocked();
            }
            pushed = true;
        } else if (!target.isWalkableBy(sokoban)) {
            return MoveResult.blocked();
        }

        sokoban.setPosition(newPos);
        return new MoveResult(true, pushed);
    }

    private boolean tryPushBox(BoardEntity box, Direction dir) {
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

        BoardEntity under = board.getFloorAt(box.getPosition());
        if (under != null) {
            under.onBoxLanded(box, dir, board);
        }
        return true;
    }

    public boolean checkVictory() {
        for (BoardEntity box : board.getBoxes()) {
            BoardEntity floor = board.getFloorAt(box.getPosition());
            if (floor == null || !floor.isGoal()) {
                return false;
            }
        }
        return true;
    }
}
