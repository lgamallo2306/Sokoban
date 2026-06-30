package sokoban.modelo.strategy;

import sokoban.modelo.Estadisticas;
import sokoban.modelo.cronometro.CronometroNivel;

/**
 * Strategy de puntaje que decora PuntajeEstandar sumando una penalizacion
 * por el tiempo transcurrido en el nivel (2 puntos por segundo).
 */
public class PuntajeConTiempo implements EstrategiaPuntaje {

    private static final int PENALIZACION_POR_SEGUNDO = 2;

    private final EstrategiaPuntaje base;
    private final CronometroNivel cronometro;

    public PuntajeConTiempo(EstrategiaPuntaje base, CronometroNivel cronometro) {
        this.base = base;
        this.cronometro = cronometro;
    }

    @Override
    public int calcular(Estadisticas s) {
        int puntajeBase = base.calcular(s);
        return Math.max(0, puntajeBase - cronometro.getSegundos() * PENALIZACION_POR_SEGUNDO);
    }
}
