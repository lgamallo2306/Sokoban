package sokoban.modelo.factory;

import sokoban.modelo.Posicion;
import sokoban.modelo.objeto.Jugador;
import sokoban.modelo.objeto.Muro;
import sokoban.modelo.objeto.ObjetoJuego;
import sokoban.modelo.objeto.Pared;
import sokoban.modelo.objeto.caja.CajaFragil;
import sokoban.modelo.objeto.caja.CajaLlave;
import sokoban.modelo.objeto.caja.CajaNormal;
import sokoban.modelo.terreno.CasillaDestino;
import sokoban.modelo.terreno.CasilleroCerrojo;
import sokoban.modelo.terreno.Piso;
import sokoban.modelo.terreno.TerrenoResbaladizo;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory que traduce caracteres a elementos del tablero.
 *
 * Mapa de caracteres de nivel:
 *   '#' Pared      ' ' Piso        '@' Jugador      '$' CajaNormal
 *   'F' CajaFragil 'K' CajaLlave   '.' Destino       '*' Caja sobre destino
 *   '+' Jugador sobre destino      '~' Resbaladizo   'C' CasilleroCerrojo
 *   'M' Muro (cerrado)
 */
public class FabricaElementos {

    private final Map<Character, CreadorElemento> charMap = new HashMap<>();

    public FabricaElementos() {
        charMap.put('#', p -> new ElementoCreado(new Piso(), new Pared(p)));
        charMap.put(' ', p -> new ElementoCreado(new Piso(), null));
        charMap.put('@', p -> new ElementoCreado(new Piso(), new Jugador(p)));
        charMap.put('$', p -> new ElementoCreado(new Piso(), new CajaNormal(p)));
        charMap.put('F', p -> new ElementoCreado(new Piso(), new CajaFragil(p)));
        charMap.put('K', p -> new ElementoCreado(new Piso(), new CajaLlave(p)));
        charMap.put('.', p -> new ElementoCreado(new CasillaDestino(), null));
        charMap.put('*', p -> new ElementoCreado(new CasillaDestino(), new CajaNormal(p)));
        charMap.put('+', p -> new ElementoCreado(new CasillaDestino(), new Jugador(p)));
        charMap.put('~', p -> new ElementoCreado(new TerrenoResbaladizo(), null));
        charMap.put('C', p -> new ElementoCreado(new CasilleroCerrojo(), null));
        charMap.put('M', p -> new ElementoCreado(new Piso(), new Muro(p)));
    }

    /** Crea terreno + objeto a partir de un caracter de nivel. */
    public ElementoCreado crearElemento(char c, Posicion p) {
        CreadorElemento creador = charMap.get(c);
        if (creador == null) {
            throw new IllegalArgumentException("Caracter de nivel desconocido: '" + c + "'");
        }
        return creador.crear(p);
    }

    /**
     * Crea solo el objeto que va encima de una celda, a partir de un caracter de la
     * capa de objetos de un Memento. El terreno ya existe y no se recrea.
     * Devuelve null para celdas sin objeto.
     */
    public ObjetoJuego crearObjeto(char c, Posicion p) {
        switch (c) {
            case '@':
                return new Jugador(p);
            case '#':
                return new Pared(p);
            case 'M':
                return new Muro(p);
            case '$':
                return new CajaNormal(p);
            case 'K':
                return new CajaLlave(p);
            default:
                if (c >= '1' && c <= '9') {
                    return new CajaFragil(p, c - '0');
                }
                return null;
        }
    }
}
