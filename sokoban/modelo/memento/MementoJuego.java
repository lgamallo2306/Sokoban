package sokoban.modelo.memento;

import sokoban.modelo.Estadisticas;

/**
 * Memento: instantanea del estado del juego (capa de objetos del tablero + estadisticas).
 * El terreno es estatico, por lo que solo se serializa la capa de objetos.
 */
public class MementoJuego {

    private final char[][] estadoTablero;
    private final Estadisticas estadoStats;

    public MementoJuego(char[][] estadoTablero, Estadisticas estadoStats) {
        this.estadoTablero = estadoTablero;
        this.estadoStats = estadoStats;
    }

    public char[][] getEstadoTablero() {
        return estadoTablero;
    }

    public Estadisticas getEstadoStats() {
        return estadoStats;
    }
}
