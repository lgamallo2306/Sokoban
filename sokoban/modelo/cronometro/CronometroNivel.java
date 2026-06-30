package sokoban.modelo.cronometro;

import sokoban.modelo.observer.EventoJuego;
import sokoban.modelo.observer.ObservadorJuego;

import javax.swing.Timer;
import java.util.function.Consumer;

/**
 * Observer que mide el tiempo transcurrido en cada nivel.
 * Se detiene con VICTORIA o PAUSA y se reanuda con REANUDA.
 * Notifica al HUD via callback en cada tick (cada segundo).
 */
public class CronometroNivel implements ObservadorJuego {

    private final Timer timer;
    private int segundos;
    private final Consumer<Integer> onTick;

    public CronometroNivel(Consumer<Integer> onTick) {
        this.onTick = onTick;
        this.timer = new Timer(1000, e -> {
            segundos++;
            onTick.accept(segundos);
        });
    }

    public int getSegundos() {
        return segundos;
    }

    @Override
    public void actualizar(EventoJuego e) {
        switch (e.getTipo()) {
            case INICIO:
            case REINICIO:
                reiniciar();
                break;
            case VICTORIA:
            case PAUSA:
                timer.stop();
                break;
            case REANUDA:
                timer.start();
                break;
            default:
                break;
        }
    }

    private void reiniciar() {
        timer.stop();
        segundos = 0;
        onTick.accept(0);
        timer.start();
    }
}
