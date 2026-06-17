package sokoban.singleton;

import sokoban.modelo.observer.EventoJuego;
import sokoban.modelo.observer.ObservadorJuego;

import javax.sound.sampled.Clip;

/**
 * Singleton que reproduce efectos de sonido reaccionando a los eventos del juego
 * (Observer). Usa GestorAssets para obtener los clips; si no hay assets, no suena.
 */
public class GestorSonido implements ObservadorJuego {

    private static final GestorSonido instancia = new GestorSonido();

    private GestorSonido() {
    }

    public static GestorSonido getInstancia() {
        return instancia;
    }

    @Override
    public void actualizar(EventoJuego e) {
        String clave = claveSonido(e.getTipo());
        if (clave != null) {
            reproducir(clave);
        }
    }

    private String claveSonido(EventoJuego.Tipo tipo) {
        switch (tipo) {
            case MOVIMIENTO:
                return "movimiento";
            case EMPUJE:
                return "empuje";
            case DESHACER:
                return "deshacer";
            case REINICIO:
                return "reinicio";
            case VICTORIA:
                return "victoria";
            case BLOQUEADO:
                return "bloqueado";
            default:
                return null;
        }
    }

    private void reproducir(String clave) {
        Clip clip = GestorAssets.getInstancia().getSonido(clave);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
