package sokoban.modelo.objeto;

import sokoban.modelo.Posicion;
import sokoban.modelo.objeto.estado.EstadoMuro;
import sokoban.modelo.objeto.estado.MuroAbierto;
import sokoban.modelo.objeto.estado.MuroCerrado;

/**
 * Muro/compuerta cuyo comportamiento depende de su estado (patron State).
 * Inicia cerrado y se abre cuando una CajaLlave llega a un CasilleroCerrojo asociado.
 */
public class Muro extends ObjetoJuego {

    private EstadoMuro estado;

    public Muro(Posicion posicion) {
        this(posicion, new MuroCerrado());
    }

    public Muro(Posicion posicion, EstadoMuro estado) {
        super(posicion);
        this.estado = estado;
    }

    public void setEstado(EstadoMuro estado) {
        this.estado = estado;
    }

    public EstadoMuro getEstado() {
        return estado;
    }

    public void abrir() {
        setEstado(new MuroAbierto());
    }

    @Override
    public boolean bloqueaPaso() {
        return !estado.puedeAtravesarse();
    }

    @Override
    public char getSimbolo() {
        return 'M';
    }
}
