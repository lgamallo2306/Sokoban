package sokoban.modelo.objeto.estado;

/**
 * Patron State: estado de un Muro (abierto / cerrado).
 */
public interface EstadoMuro {
    boolean puedeAtravesarse();
}
