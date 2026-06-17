package sokoban.modelo.objeto;

import sokoban.modelo.Direccion;
import sokoban.modelo.Posicion;

/**
 * Objeto que ocupa una celda del tablero (jugador, pared, muro, caja).
 * Cada celda admite a lo sumo un ObjetoJuego.
 */
public abstract class ObjetoJuego {

    protected Posicion posicion;
    private Direccion ultimaDireccion;

    protected ObjetoJuego(Posicion posicion) {
        this.posicion = posicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    /** Ultima direccion en la que se movio el objeto (usada por terrenos como el resbaladizo). */
    public Direccion getUltimaDireccion() {
        return ultimaDireccion;
    }

    public void setUltimaDireccion(Direccion ultimaDireccion) {
        this.ultimaDireccion = ultimaDireccion;
    }

    /** Indica si el jugador puede empujar este objeto. */
    public boolean esEmpujable() {
        return false;
    }

    /** Indica si el objeto impide el paso a su celda. */
    public boolean bloqueaPaso() {
        return true;
    }

    /** Simbolo usado para serializar el objeto (Memento / depuracion). */
    public abstract char getSimbolo();
}
