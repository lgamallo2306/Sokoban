package sokoban.modelo.terreno;

import sokoban.modelo.Direccion;
import sokoban.modelo.Posicion;
import sokoban.modelo.Tablero;
import sokoban.modelo.objeto.ObjetoJuego;

/**
 * Hielo: el objeto que entra sigue deslizandose en su direccion de movimiento
 * hasta chocar con un obstaculo o salir de una zona resbaladiza.
 */
public class TerrenoResbaladizo extends Terreno {

    @Override
    public void alEntrar(ObjetoJuego o, Tablero t) {
        Direccion d = o.getUltimaDireccion();
        if (d == null) {
            return;
        }
        Posicion actual = o.getPosicion();
        while (true) {
            Posicion siguiente = actual.trasladar(d);
            if (!t.puedeMoverObjetoA(siguiente)) {
                break;
            }
            t.moverObjeto(actual, siguiente);
            actual = siguiente;
            Terreno terreno = t.getCelda(actual).getTerreno();
            if (!(terreno instanceof TerrenoResbaladizo)) {
                // Al detenerse, dispara el efecto del terreno de llegada.
                terreno.alEntrar(o, t);
                break;
            }
        }
    }

    @Override
    public char getSimbolo() {
        return '~';
    }
}
