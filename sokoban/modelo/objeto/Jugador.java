package sokoban.modelo.objeto;

import sokoban.modelo.Posicion;

/**
 * El jugador (Sokoban). Se mueve por el tablero y empuja cajas.
 */
public class Jugador extends ObjetoJuego {

    public Jugador(Posicion posicion) {
        super(posicion);
    }

    @Override
    public boolean bloqueaPaso() {
        return true;
    }

    @Override
    public char getSimbolo() {
        return '@';
    }
}
