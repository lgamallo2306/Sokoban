package sokoban.model;

import sokoban.model.entity.BoardEntity;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<BoardEntity> entities;
    private final int rows;
    private final int cols;

    public Board(List<BoardEntity> entities, int rows, int cols) {
        this.entities = new ArrayList<>(entities);
        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<BoardEntity> getEntities() {
        return entities;
    }

    public BoardEntity getEntityAt(Position pos) {
        for (BoardEntity entity : entities) {
            if (!entity.isFloor() && entity.getPosition().equals(pos)) {
                return entity;
            }
        }
        return getFloorAt(pos);
    }

    public BoardEntity getFloorAt(Position pos) {
        for (BoardEntity entity : entities) {
            if (entity.isFloor() && entity.getPosition().equals(pos)) {
                return entity;
            }
        }
        return null;
    }

    public boolean hasLockAt(Position pos) {
        for (BoardEntity entity : entities) {
            if (entity.isLock() && entity.getPosition().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public void openAllGates() {
        for (BoardEntity entity : entities) {
            entity.openGate();
        }
    }

    public List<BoardEntity> getBoxes() {
        List<BoardEntity> boxes = new ArrayList<>();
        for (BoardEntity entity : entities) {
            if (entity.isBox()) {
                boxes.add(entity);
            }
        }
        return boxes;
    }

    public boolean isInside(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < rows
                && pos.getCol() >= 0 && pos.getCol() < cols;
    }

    public void removeEntity(BoardEntity entity) {
        entities.remove(entity);
    }

    public void addEntity(BoardEntity entity) {
        entities.add(entity);
    }
}
