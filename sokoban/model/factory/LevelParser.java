package sokoban.model.factory;

import sokoban.model.Board;
import sokoban.model.Position;
import sokoban.model.entity.BoardEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelParser {

    private final BoardEntityFactory factory;

    public LevelParser() {
        this(new BoardEntityFactory());
    }

    public LevelParser(BoardEntityFactory factory) {
        this.factory = factory;
    }

    public Board parse(String path) {
        List<String> lines = readLines(path);
        int rows = lines.size();
        int cols = lines.stream().mapToInt(String::length).max().orElse(0);
        List<BoardEntity> entities = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            String line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                Position pos = new Position(row, col);
                entities.addAll(factory.create(c, pos));
            }
        }
        return new Board(entities, rows, cols);
    }

    private List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer nivel: " + path, e);
        }
        return lines;
    }
}
