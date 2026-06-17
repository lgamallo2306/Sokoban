package sokoban.modelo;

import sokoban.modelo.objeto.ObjetoJuego;
import sokoban.modelo.terreno.Terreno;

/**
 * Celda del tablero: un terreno fijo y, opcionalmente, un objeto encima.
 */
public class Celda {

    private final Terreno terreno;
    private ObjetoJuego objeto;

    public Celda(Terreno terreno) {
        this(terreno, null);
    }

    public Celda(Terreno terreno, ObjetoJuego objeto) {
        this.terreno = terreno;
        this.objeto = objeto;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public ObjetoJuego getObjeto() {
        return objeto;
    }

    public void setObjeto(ObjetoJuego objeto) {
        this.objeto = objeto;
    }

    /** Una celda esta libre cuando no tiene ningun objeto encima. */
    public boolean estaLibre() {
        return objeto == null;
    }
}
