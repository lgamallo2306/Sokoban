package sokoban.modelo.terreno;

import sokoban.modelo.Tablero;
import sokoban.modelo.objeto.ObjetoJuego;

/**
 * Casilla objetivo: el nivel se gana cuando todas tienen una caja encima.
 */
public class CasillaDestino extends Terreno {

    @Override
    public void alEntrar(ObjetoJuego o, Tablero t) {
        // Sin efecto: la victoria se evalua en Tablero.esVictoria().
    }

    @Override
    public boolean esDestino() {
        return true;
    }

    @Override
    public char getSimbolo() {
        return '.';
    }
}
