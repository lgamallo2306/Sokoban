package sokoban.modelo.objeto.caja;

import sokoban.modelo.Posicion;
import sokoban.modelo.Tablero;
import sokoban.modelo.objeto.ObjetoJuego;

/**
 * Caja empujable. Cada subtipo encapsula su reaccion al ser empujada (Strategy).
 */
public abstract class Caja extends ObjetoJuego {

    protected Caja(Posicion posicion) {
        super(posicion);
    }

    @Override
    public boolean esEmpujable() {
        return true;
    }

    @Override
    public boolean bloqueaPaso() {
        return true;
    }

    /** Reaccion de la caja al ser empujada (ya reubicada en su nueva celda). */
    public abstract void alSerEmpujada(Tablero t);
}
