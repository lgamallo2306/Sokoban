package sokoban.modelo.objeto.estado;

public class MuroCerrado implements EstadoMuro {
    @Override
    public boolean puedeAtravesarse() {
        return false;
    }
}
