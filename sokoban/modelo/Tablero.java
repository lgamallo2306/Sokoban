package sokoban.modelo;

import sokoban.modelo.objeto.Jugador;
import sokoban.modelo.objeto.Muro;
import sokoban.modelo.objeto.ObjetoJuego;
import sokoban.modelo.objeto.caja.Caja;
import sokoban.modelo.terreno.CasilleroCerrojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Grilla de celdas. Conoce que hay en cada posicion y sabe mover objetos
 * y evaluar la condicion de victoria (Information Expert).
 */
public class Tablero {

    private final Celda[][] celdas;
    private final int filas;
    private final int columnas;
    private Jugador jugador;

    public Tablero(Celda[][] celdas) {
        this.celdas = celdas;
        this.filas = celdas.length;
        this.columnas = filas > 0 ? celdas[0].length : 0;
        this.jugador = localizarJugador();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Celda getCelda(Posicion p) {
        return celdas[p.getFila()][p.getColumna()];
    }

    public boolean estaDentro(Posicion p) {
        return p.getFila() >= 0 && p.getFila() < filas
                && p.getColumna() >= 0 && p.getColumna() < columnas;
    }

    /** Una posicion admite recibir un objeto si esta dentro y su celda esta libre. */
    public boolean puedeMoverObjetoA(Posicion p) {
        return estaDentro(p) && getCelda(p).estaLibre();
    }

    /** Mueve el objeto de la celda origen a la celda destino y actualiza su posicion. */
    public void moverObjeto(Posicion origen, Posicion destino) {
        ObjetoJuego obj = getCelda(origen).getObjeto();
        getCelda(origen).setObjeto(null);
        getCelda(destino).setObjeto(obj);
        if (obj != null) {
            obj.setPosicion(destino);
        }
    }

    public void removerObjeto(ObjetoJuego o) {
        if (o != null && estaDentro(o.getPosicion())) {
            Celda c = getCelda(o.getPosicion());
            if (c.getObjeto() == o) {
                c.setObjeto(null);
            }
        }
    }

    /** Victoria: existe al menos un destino y todos los destinos tienen una caja encima. */
    public boolean esVictoria() {
        boolean hayDestinos = false;
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                Celda celda = celdas[f][c];
                if (celda.getTerreno().esDestino()) {
                    hayDestinos = true;
                    if (!(celda.getObjeto() instanceof Caja)) {
                        return false;
                    }
                }
            }
        }
        return hayDestinos;
    }

    public List<Caja> getCajas() {
        List<Caja> cajas = new ArrayList<>();
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                ObjetoJuego o = celdas[f][c].getObjeto();
                if (o instanceof Caja) {
                    cajas.add((Caja) o);
                }
            }
        }
        return cajas;
    }

    /** Asocia todos los muros del tablero a cada casillero cerrojo. */
    public void vincularCerrojos() {
        List<Muro> muros = new ArrayList<>();
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                ObjetoJuego o = celdas[f][c].getObjeto();
                if (o instanceof Muro) {
                    muros.add((Muro) o);
                }
            }
        }
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (celdas[f][c].getTerreno() instanceof CasilleroCerrojo) {
                    ((CasilleroCerrojo) celdas[f][c].getTerreno()).setMurosAsociados(muros);
                }
            }
        }
    }

    /** Vuelve a localizar al jugador tras una restauracion de estado. */
    public void recalcularJugador() {
        this.jugador = localizarJugador();
    }

    private Jugador localizarJugador() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                ObjetoJuego o = celdas[f][c].getObjeto();
                if (o instanceof Jugador) {
                    return (Jugador) o;
                }
            }
        }
        return null;
    }
}
