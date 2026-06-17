package sokoban.modelo.observer;

import sokoban.modelo.Estadisticas;
import sokoban.modelo.Tablero;

/**
 * Mensaje que el Juego envia a sus observadores describiendo un cambio de estado.
 */
public class EventoJuego {

    public enum Tipo {
        INICIO,
        MOVIMIENTO,
        EMPUJE,
        BLOQUEADO,
        DESHACER,
        REINICIO,
        VICTORIA
    }

    private final Tipo tipo;
    private final Tablero tablero;
    private final Estadisticas estadisticas;
    private final int nivelActual;
    private final boolean victoria;
    private final int puntaje;

    public EventoJuego(Tipo tipo, Tablero tablero, Estadisticas estadisticas,
                       int nivelActual, boolean victoria, int puntaje) {
        this.tipo = tipo;
        this.tablero = tablero;
        this.estadisticas = estadisticas;
        this.nivelActual = nivelActual;
        this.victoria = victoria;
        this.puntaje = puntaje;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
