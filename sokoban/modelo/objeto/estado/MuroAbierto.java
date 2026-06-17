package sokoban.modelo.objeto.estado;

public class MuroAbierto implements EstadoMuro {
    @Override
    public boolean puedeAtravesarse() {
        return true;
    }
}
