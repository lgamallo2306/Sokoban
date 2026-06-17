package sokoban.modelo.objeto.caja;

import sokoban.modelo.Posicion;
import sokoban.modelo.Tablero;

/**
 * Caja fragil: cada empuje resta resistencia. Al llegar a 0 se destruye
 * (se elimina del tablero).
 */
public class CajaFragil extends Caja {

    public static final int RESISTENCIA_INICIAL = 2;

    private int resistencia;

    public CajaFragil(Posicion posicion) {
        this(posicion, RESISTENCIA_INICIAL);
    }

    public CajaFragil(Posicion posicion, int resistencia) {
        super(posicion);
        this.resistencia = resistencia;
    }

    public int getResistencia() {
        return resistencia;
    }

    @Override
    public boolean esEmpujable() {
        return resistencia > 0;
    }

    @Override
    public void alSerEmpujada(Tablero t) {
        resistencia--;
        if (resistencia <= 0) {
            t.removerObjeto(this);
        }
    }

    @Override
    public char getSimbolo() {
        // Codifica la resistencia actual para que el Memento la preserve.
        return (char) ('0' + Math.max(0, Math.min(9, resistencia)));
    }
}
