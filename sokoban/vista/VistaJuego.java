package sokoban.vista;

import sokoban.controlador.ControladorJuego;
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

    public VistaJuego() {
        super("Sokoban");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(panelHUD, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);
    }

    /** Enlaza el controlador: teclado en la ventana y acciones de los botones. */
    public void inicializar(ControladorJuego controlador) {
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

        if (e.getTipo() == EventoJuego.Tipo.REINICIO || e.getTipo() == EventoJuego.Tipo.INICIO) {
            victoriaMostrada = false;
        }
        if (e.isVictoria() && !victoriaMostrada) {
            victoriaMostrada = true;
            int puntaje = e.getPuntaje();
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                    "Nivel completado!\nPuntaje: " + puntaje,
                    "Victoria", JOptionPane.INFORMATION_MESSAGE));
        }
    }
}
