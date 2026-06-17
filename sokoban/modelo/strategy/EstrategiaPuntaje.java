package sokoban.modelo.strategy;

import sokoban.modelo.Estadisticas;

/**
 * Patron Strategy: forma de calcular el puntaje al completar un nivel.
 */
public interface EstrategiaPuntaje {
    int calcular(Estadisticas s);
}
