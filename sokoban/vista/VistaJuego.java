package sokoban.vista;

import sokoban.controlador.ControladorJuego;
import sokoban.modelo.cronometro.CronometroNivel;
import sokoban.modelo.observer.EventoJuego;
import sokoban.modelo.observer.ObservadorJuego;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

/**
 * Ventana principal (Vista en MVC). Observa al juego y reparte cada evento
 * entre el panel del tablero y el HUD.
 */
public class VistaJuego extends JFrame implements ObservadorJuego {

    private final PanelTablero panelTablero = new PanelTablero();
    private final PanelHUD panelHUD = new PanelHUD();
    private boolean victoriaMostrada;
    private ControladorJuego controlador;
    private CronometroNivel cronometro;

    public VistaJuego() {
        super("Sokoban");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(panelHUD, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);
    }

    public void setCronometro(CronometroNivel cronometro) {
        this.cronometro = cronometro;
    }

    public void actualizarTiempo(int segundos) {
        panelHUD.actualizarTiempo(segundos);
    }

    /** Enlaza el controlador: teclado en la ventana y acciones de los botones. */
    public void inicializar(ControladorJuego controlador) {
        this.controlador = controlador;
        addKeyListener(controlador);
        panelHUD.conectar(controlador);
        setFocusable(true);
        pack();
        setLocationRelativeTo(null);
        requestFocusInWindow();
    }

    @Override
    public void actualizar(EventoJuego e) {
        panelTablero.render(e.getTablero());
        panelHUD.actualizar(e);

        if (e.getTipo() == EventoJuego.Tipo.PAUSA) {
            panelTablero.setPausado(true);
        } else if (e.getTipo() == EventoJuego.Tipo.REANUDA) {
            panelTablero.setPausado(false);
        }

        if (e.getTipo() == EventoJuego.Tipo.REINICIO || e.getTipo() == EventoJuego.Tipo.INICIO) {
            panelTablero.setPausado(false);
            victoriaMostrada = false;
        }
        if (e.isVictoria() && !victoriaMostrada) {
            victoriaMostrada = true;
            SwingUtilities.invokeLater(() -> mostrarResumenYAvanzar(e));
        }
    }

    private void mostrarResumenYAvanzar(EventoJuego e) {
        int tiempoFinal = cronometro != null ? cronometro.getSegundos() : 0;
        String mensaje = String.format(
                "Nivel %d completado!\n\n" +
                "Tiempo       : %ds\n" +
                "Movimientos  : %d\n" +
                "Empujes      : %d\n" +
                "Usos de undo : %d\n\n" +
                "Puntaje final: %d",
                e.getNivelActual(),
                tiempoFinal,
                e.getEstadisticas().getMovimientos(),
                e.getEstadisticas().getEmpujes(),
                e.getEstadisticas().getUsosUndo(),
                e.getPuntaje());

        if (controlador.hayNivelSiguiente()) {
            JOptionPane.showMessageDialog(this, mensaje, "Nivel completado", JOptionPane.INFORMATION_MESSAGE);
            controlador.onSiguienteNivel();
            requestFocusInWindow();
        } else {
            JOptionPane.showMessageDialog(this, mensaje + "\n\n¡Juego completado!", "Victoria", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
