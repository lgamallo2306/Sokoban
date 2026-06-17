package sokoban.modelo.objeto;

import sokoban.modelo.Posicion;

/**
 * Pared inmovible: bloquea siempre el paso y no es empujable.
 */
public class Pared extends ObjetoJuego {

    public Pared(Posicion posicion) {
        super(posicion);
    }

    @Override
    public boolean bloqueaPaso() {
        return true;
    }

    @Override
    public char getSimbolo() {
        return '#';
    }
}
