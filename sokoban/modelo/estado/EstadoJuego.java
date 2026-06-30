package sokoban.modelo.estado;

/**
 * State: contrato para los estados posibles del juego (jugando / pausado).
 * Determina si el estado acepta acciones del jugador.
 */
public interface EstadoJuego {
    boolean aceptaAcciones();
}
