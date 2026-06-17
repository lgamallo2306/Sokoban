package sokoban.modelo.terreno;

import sokoban.modelo.Tablero;
import sokoban.modelo.objeto.ObjetoJuego;
import sokoban.modelo.objeto.Muro;
import sokoban.modelo.objeto.caja.CajaLlave;

import java.util.ArrayList;
import java.util.List;

/**
 * Cerradura del piso: cuando una CajaLlave aterriza encima, abre los muros asociados.
 */
public class CasilleroCerrojo extends Terreno {

    private final List<Muro> murosAsociados = new ArrayList<>();

    public void setMurosAsociados(List<Muro> muros) {
        murosAsociados.clear();
        murosAsociados.addAll(muros);
    }

    public List<Muro> getMurosAsociados() {
        return murosAsociados;
    }

    @Override
    public void alEntrar(ObjetoJuego o, Tablero t) {
        if (o instanceof CajaLlave) {
            for (Muro muro : murosAsociados) {
                muro.abrir();
                t.removerObjeto(muro);
            }
        }
    }

    @Override
    public char getSimbolo() {
        return 'C';
    }
}
