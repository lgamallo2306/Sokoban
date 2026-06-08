package sokoban.controller;

import sokoban.dto.BoardEntityDTO;
import sokoban.dto.EntityType;
import sokoban.dto.GameStateDTO;
import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.GameModel;
import sokoban.model.entity.BoardEntity;
import sokoban.model.entity.Sokoban;
import sokoban.model.entity.box.FragileBox;
import sokoban.model.entity.box.KeyBox;
import sokoban.model.entity.box.NormalBox;
import sokoban.model.entity.floor.Empty;
import sokoban.model.entity.floor.Slippery;
import sokoban.model.entity.floor.Target;
import sokoban.model.entity.obstacle.Gate;
import sokoban.model.entity.obstacle.Lock;
import sokoban.model.entity.obstacle.Wall;
import sokoban.model.observer.GameStatsObserver;
import sokoban.model.factory.LevelParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameController {

    private final LevelParser levelParser;
    private final GameModel model;

    public GameController() {
        this(new LevelParser());
    }

    public GameController(LevelParser levelParser) {
        this.levelParser = levelParser;
        this.model = new GameModel();
    }

    public void loadLevel(String path, int nivelActual) {
        Board board = levelParser.parse(path);
        BoardEntity sokoban = findSokoban(board);
        if (sokoban == null) {
            throw new IllegalStateException("El nivel no contiene un Sokoban (@)");
        }
        this.model.loadLevel(board, sokoban, nivelActual, path);
    }

    public void handleInput(Direction dir) {
        model.move(dir);
    }

    public GameStateDTO getCurrentState() {
        requireModel();
        Board board = model.getBoard();
        List<BoardEntityDTO> dtos = mapEntitiesToDTOs(board.getEntities());
        dtos.sort(Comparator.comparingInt(dto -> dto.getTipo().getRenderPriority()));
        return new GameStateDTO(
                dtos,
                board.getRows(),
                board.getCols(),
                model.getStats().getMovimientos(),
                model.getStats().getEmpujes(),
                model.getNivelActual(),
                model.checkVictory());
    }

    public void addStatsObserver(GameStatsObserver observer) {
        model.getStats().addObserver(observer);
    }

    public boolean isVictoria() {
        return model.isLoaded() && model.checkVictory();
    }

    public void restartLevel() {
        if (model.isLoaded() && model.getCurrentLevelPath() != null) {
            loadLevel(model.getCurrentLevelPath(), model.getNivelActual());
        }
    }

    public void undo() {
        // TODO: implementar con patrón Memento
    }

    // ── Mapeo de entidades del modelo a DTOs ──────────────────

    private List<BoardEntityDTO> mapEntitiesToDTOs(List<BoardEntity> entities) {
        List<BoardEntityDTO> dtos = new ArrayList<>();
        for (BoardEntity entity : entities) {
            EntityType tipo = resolveEntityType(entity);
            String label = resolveLabel(entity);
            dtos.add(new BoardEntityDTO(entity.getPosition(), tipo, label));
        }
        return dtos;
    }

    private EntityType resolveEntityType(BoardEntity entity) {
        if (entity instanceof Wall)       return EntityType.WALL;
        if (entity instanceof Sokoban)    return EntityType.SOKOBAN;
        if (entity instanceof KeyBox)     return EntityType.KEY_BOX;
        if (entity instanceof FragileBox) return EntityType.FRAGILE_BOX;
        if (entity instanceof NormalBox)  return EntityType.NORMAL_BOX;
        if (entity instanceof Target)    return EntityType.TARGET;
        if (entity instanceof Slippery)  return EntityType.SLIPPERY;
        if (entity instanceof Lock)      return EntityType.LOCK;
        if (entity instanceof Gate) {
            return ((Gate) entity).isOpen() ? EntityType.GATE_OPEN : EntityType.GATE_CLOSED;
        }
        if (entity instanceof Empty)     return EntityType.EMPTY;
        return EntityType.EMPTY;
    }

    private String resolveLabel(BoardEntity entity) {
        if (entity instanceof FragileBox) {
            return String.valueOf(((FragileBox) entity).getResistance());
        }
        return "";
    }

    // ── Utilidades internas ───────────────────────────────────

    private BoardEntity findSokoban(Board board) {
        for (BoardEntity entity : board.getEntities()) {
            if (entity.isSokoban()) {
                return entity;
            }
        }
        return null;
    }

    private void requireModel() {
        if (!model.isLoaded()) {
            throw new IllegalStateException("No hay nivel cargado");
        }
    }
}
