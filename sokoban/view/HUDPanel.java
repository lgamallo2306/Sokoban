package sokoban.view;

import sokoban.model.observer.GameStatsObserver;

import javax.swing.*;
import java.awt.*;

public class HUDPanel extends JPanel implements GameStatsObserver {

    private final JLabel movimientosLabel = new JLabel("Movimientos: 0");
    private final JLabel empujesLabel     = new JLabel("Empujes: 0");

    public HUDPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        add(movimientosLabel);
        add(empujesLabel);
    }

    @Override
    public void onStatsUpdated(int movimientos, int empujes) {
        movimientosLabel.setText("Movimientos: " + movimientos);
        empujesLabel.setText("Empujes: " + empujes);
        repaint();
    }
}
