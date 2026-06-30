package sokoban.modelo.estado;

public class EstadoJugando implements EstadoJuego {
    @Override
    public boolean aceptaAcciones() {
        return true;
    }
}
