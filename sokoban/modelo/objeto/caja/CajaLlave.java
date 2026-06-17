package sokoban.modelo.objeto.caja;

import sokoban.modelo.Posicion;
import sokoban.modelo.Tablero;

/**
 * Caja llave: no reacciona por si misma al ser empujada. La apertura de muros
 * la decide el CasilleroCerrojo cuando esta caja aterriza sobre el (Information Expert).
 */
public class CajaLlave extends Caja {

    public CajaLlave(Posicion posicion) {
        super(posicion);
    }

    @Override
    public void alSerEmpujada(Tablero t) {
        // El terreno CasilleroCerrojo es quien abre los muros al recibir la llave.
    }

    @Override
    public char getSimbolo() {
        return 'K';
    }
}
