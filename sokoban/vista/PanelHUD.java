package sokoban.vista;

import sokoban.controlador.ControladorJuego;
import sokoban.modelo.observer.EventoJuego;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;

/**
 * Panel superior con los contadores y los botones de deshacer / reiniciar.
 */
public class PanelHUD extends JPanel {

    private final JLabel movimientos = new JLabel();
    private final JLabel empujes = new JLabel();
    private final JLabel nivel = new JLabel();
    private final JLabel tiempo = new JLabel("Tiempo: 0s");
    private final JButton btnUndo = new JButton("Deshacer (Z)");
    private final JButton btnReiniciar = new JButton("Reiniciar (R)");
    private final JButton btnPausa = new JButton("Pausa (P)");

    public PanelHUD() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 8));
        add(nivel);
        add(movimientos);
        add(empujes);
        add(tiempo);
        add(btnUndo);
        add(btnReiniciar);
        add(btnPausa);
        actualizarLabels(1, 0, 0);
    }

    /** Conecta los botones con el controlador. */
    public void conectar(ControladorJuego controlador) {
        btnUndo.addActionListener(e -> controlador.onUndo());
        btnReiniciar.addActionListener(e -> controlador.onReiniciar());
        btnPausa.addActionListener(e -> controlador.onTogglePausa());
        btnUndo.setFocusable(false);
        btnReiniciar.setFocusable(false);
        btnPausa.setFocusable(false);
    }

    public void actualizar(EventoJuego e) {
        actualizarLabels(e.getNivelActual(),
                e.getEstadisticas().getMovimientos(),
                e.getEstadisticas().getEmpujes());
        boolean pausado = e.getTipo() == EventoJuego.Tipo.PAUSA;
        boolean reanudado = e.getTipo() == EventoJuego.Tipo.REANUDA;
        if (pausado) {
            btnPausa.setText("Reanudar (P)");
        } else if (reanudado) {
            btnPausa.setText("Pausa (P)");
        }
    }

    public void actualizarTiempo(int segundos) {
        tiempo.setText("Tiempo: " + segundos + "s");
    }

    private void actualizarLabels(int nivelActual, int movs, int emp) {
        nivel.setText("Nivel: " + nivelActual);
        movimientos.setText("Movimientos: " + movs);
        empujes.setText("Empujes: " + emp);
    }
}
