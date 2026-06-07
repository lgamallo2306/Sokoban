package sokoban.controller;

import sokoban.dto.GameStateDTO;
import sokoban.model.Board;
import sokoban.model.Direction;
import sokoban.model.GameEngine;
import sokoban.model.GameStats;
import sokoban.model.MoveResult;
import sokoban.model.entity.BoardEntity;
import sokoban.model.observer.GameStatsObserver;
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
        BoardEntity sokoban = findSokoban(board);
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
        
        List<BoardEntityDTO> entityDTOs = new ArrayList<>();
        for (BoardEntity entity : board.getEntities()) {
            BoardEntityDTO dto = new BoardEntityDTO(
                entity.getPosition(),
                entity.getType()
            );
            entityDTOs.add(dto);
        }
        
        return new GameStateDTO(
                entityDTOs,
                board.getRows(),
                board.getCols(),
                stats.getMovimientos(),
                stats.getEmpujes(),
                nivelActual,
                engine.checkVictory());
    }

    public void addStatsObserver(GameStatsObserver observer) {
        stats.addObserver(observer);
    }

    public boolean isVictoria() {
        return engine != null && engine.checkVictory();
    }

    private BoardEntity findSokoban(Board board) {
        for (BoardEntity entity : board.getEntities()) {
            if (entity.isSokoban()) {
                return entity;
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
