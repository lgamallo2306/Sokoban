package sokoban.modelo.factory;

import sokoban.modelo.Posicion;

/**
 * Funcion que crea el ElementoCreado correspondiente a un caracter del nivel.
 */
@FunctionalInterface
public interface CreadorElemento {
    ElementoCreado crear(Posicion p);
}
