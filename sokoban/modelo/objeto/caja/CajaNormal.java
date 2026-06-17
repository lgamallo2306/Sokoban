package sokoban.modelo.objeto.caja;

import sokoban.modelo.Posicion;
import sokoban.modelo.Tablero;

/**
 * Caja comun: al ser empujada no hace nada especial.
 */
public class CajaNormal extends Caja {

    public CajaNormal(Posicion posicion) {
        super(posicion);
    }

    @Override
    public void alSerEmpujada(Tablero t) {
        // Sin comportamiento adicional.
    }

    @Override
    public char getSimbolo() {
        return '$';
    }
}
