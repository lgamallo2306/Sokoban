package sokoban.modelo.terreno;

import sokoban.modelo.Tablero;
import sokoban.modelo.objeto.ObjetoJuego;

/**
 * Piso comun: no produce ningun efecto al entrar.
 */
public class Piso extends Terreno {

    @Override
    public void alEntrar(ObjetoJuego o, Tablero t) {
        // Sin efecto.
    }

    @Override
    public char getSimbolo() {
        return ' ';
    }
}
