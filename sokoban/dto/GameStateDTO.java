package sokoban.dto;

import java.util.Collections;
import java.util.List;

public class GameStateDTO {

    private final List<BoardEntityDTO> entities;
    private final int rows;
    private final int cols;
    private final int movimientos;
    private final int empujes;
    private final int nivelActual;
    private final boolean victoria;

    public GameStateDTO(List<BoardEntityDTO> entities,
                        int rows,
                        int cols,
                        int movimientos,
                        int empujes,
                        int nivelActual,
                        boolean victoria) {
        this.entities = Collections.unmodifiableList(entities);
        this.rows = rows;
        this.cols = cols;
        this.movimientos = movimientos;
        this.empujes = empujes;
        this.nivelActual = nivelActual;
        this.victoria = victoria;
    }

    public List<BoardEntityDTO> getEntities() {
        return entities;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMovimientos() {
        return movimientos;
    }

    public int getEmpujes() {
        return empujes;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean isVictoria() {
        return victoria;
    }
}
