package sokoban.modelo.strategy;

import sokoban.modelo.Estadisticas;

/**
 * Puntaje estandar: parte de una base y penaliza movimientos, empujes y undos.
 */
public class PuntajeEstandar implements EstrategiaPuntaje {

    private static final int BASE = 1000;

    @Override
    public int calcular(Estadisticas s) {
        int puntaje = BASE
                - s.getMovimientos() * 5
                - s.getEmpujes() * 2
                - s.getUsosUndo() * 25;
        return Math.max(0, puntaje);
    }
}
