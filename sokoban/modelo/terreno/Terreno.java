package sokoban.modelo.terreno;

import sokoban.modelo.Tablero;
import sokoban.modelo.objeto.ObjetoJuego;

/**
 * Suelo de una celda. Reacciona cuando un objeto entra en ella.
 */
public abstract class Terreno {

    /** Se invoca cuando un objeto acaba de entrar a la celda de este terreno. */
    public abstract void alEntrar(ObjetoJuego o, Tablero t);

    /** Indica si esta casilla es un objetivo (donde deben quedar las cajas). */
    public boolean esDestino() {
        return false;
    }

    /** Simbolo base del terreno (sin objeto encima). */
    public abstract char getSimbolo();
}
