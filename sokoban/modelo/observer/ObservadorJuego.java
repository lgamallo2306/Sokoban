package sokoban.modelo.observer;

/**
 * Patron Observer: cualquier componente interesado en los cambios del juego.
 */
public interface ObservadorJuego {
    void actualizar(EventoJuego e);
}
