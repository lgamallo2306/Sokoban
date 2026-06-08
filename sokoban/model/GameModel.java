package sokoban.model;

import sokoban.model.entity.BoardEntity;

public class GameModel {

    private Board board;
    private BoardEntity sokoban;
    private final GameStats stats;

    private int nivelActual;
    private String currentLevelPath;

    public GameModel() {
        this.stats = new GameStats();
    }

    public void loadLevel(Board board, BoardEntity sokoban, int nivelActual, String currentLevelPath) {
        this.board = board;
        this.sokoban = sokoban;
        this.nivelActual = nivelActual;
        this.currentLevelPath = currentLevelPath;
        this.stats.reset();
    }

    public Board getBoard() {
        return board;
    }

    public BoardEntity getSokoban() {
        return sokoban;
    }

    public GameStats getStats() {
        return stats;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public String getCurrentLevelPath() {
        return currentLevelPath;
    }

    public boolean isLoaded() {
        return board != null && sokoban != null;
    }

    public void move(Direction dir) {
        if (!isLoaded()) return;

        Position newPos = sokoban.getPosition().translate(dir);
        if (!board.isInside(newPos)) {
            stats.record(MoveResult.blocked());
            return;
        }

        BoardEntity target = board.getEntityAt(newPos);

        boolean pushed = false;
        if (target.canBePushed()) {
            if (!tryPushBox(target, dir)) {
                stats.record(MoveResult.blocked());
                return;
            }
            pushed = true;
        } else if (!target.isWalkableBy(sokoban)) {
            stats.record(MoveResult.blocked());
            return;
        }

        sokoban.setPosition(newPos);
        stats.record(new MoveResult(true, pushed));
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
        if (!isLoaded()) return false;
        for (BoardEntity box : board.getBoxes()) {
            BoardEntity floor = board.getFloorAt(box.getPosition());
            if (floor == null || !floor.isGoal()) {
                return false;
            }
        }
        return true;
    }
}
