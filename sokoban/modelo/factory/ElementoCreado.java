package sokoban.modelo.factory;

import sokoban.modelo.objeto.ObjetoJuego;
import sokoban.modelo.terreno.Terreno;

/**
 * Resultado de interpretar un caracter del nivel: el terreno de la celda y,
 * opcionalmente, el objeto que arranca encima.
 */
public class ElementoCreado {

    private final Terreno terreno;
    private final ObjetoJuego objeto;

    public ElementoCreado(Terreno terreno, ObjetoJuego objeto) {
        this.terreno = terreno;
        this.objeto = objeto;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public ObjetoJuego getObjeto() {
        return objeto;
    }
}
