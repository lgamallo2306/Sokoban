package sokoban.modelo.estado;

public class EstadoPausado implements EstadoJuego {
    @Override
    public boolean aceptaAcciones() {
        return false;
    }
}
