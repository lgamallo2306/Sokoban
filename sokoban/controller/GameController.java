package sokoban.controller;

import sokoban.dto.BoardEntityDTO;
import sokoban.dto.GameStateDTO;
import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.GameEngine;
import sokoban.model.GameStats;
import sokoban.model.MoveResult;
import sokoban.model.entity.BoardEntity;
import sokoban.model.entity.Sokoban;
import sokoban.model.factory.LevelParser;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final LevelParser levelParser;
    private final GameStats stats;

    private GameEngine engine;
    private int nivelActual;

    public GameController() {
        this(new LevelParser());
    }

    public GameController(LevelParser levelParser) {
        this.levelParser = levelParser;
        this.stats = new GameStats();
    }

    public void loadLevel(String path, int nivelActual) {
        Board board = levelParser.parse(path);
        Sokoban sokoban = findSokoban(board);
        if (sokoban == null) {
            throw new IllegalStateException("El nivel no contiene un Sokoban (@)");
        }
        this.engine = new GameEngine(board, sokoban);
        this.nivelActual = nivelActual;
        this.stats.reset();
    }

    public void handleInput(Direction dir) {
        requireEngine();
        MoveResult result = engine.move(dir);
        stats.record(result);
    }

    public GameStateDTO getCurrentState() {
        requireEngine();
        Board board = engine.getBoard();
        List<BoardEntityDTO> dtos = new ArrayList<>();
        for (BoardEntity entity : board.getEntities()) {
            dtos.add(new BoardEntityDTO(entity.getPosition(), entity.getType()));
        }
        return new GameStateDTO(
                dtos,
                board.getRows(),
                board.getCols(),
                stats.getMovimientos(),
                stats.getEmpujes(),
                nivelActual,
                engine.checkVictory());
    }

    public boolean isVictoria() {
        return engine != null && engine.checkVictory();
    }

    private Sokoban findSokoban(Board board) {
        for (BoardEntity entity : board.getEntities()) {
            if (entity instanceof Sokoban) {
                return (Sokoban) entity;
            }
        }
        return null;
    }

    private void requireEngine() {
        if (engine == null) {
            throw new IllegalStateException("No hay nivel cargado");
        }
    }
}
