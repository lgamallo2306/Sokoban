package sokoban.model;

import sokoban.model.observer.GameStatsObserver;

import java.util.ArrayList;
import java.util.List;

public class GameStats {

    private int movimientos;
    private int empujes;
    private final List<GameStatsObserver> observers = new ArrayList<>();

    public void addObserver(GameStatsObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameStatsObserver observer) {
        observers.remove(observer);
    }

    public void record(MoveResult result) {
        if (result.isMoved())  movimientos++;
        if (result.isPushed()) empujes++;
        notifyObservers();
    }

    public int getMovimientos() {
        return movimientos;
    }

    public int getEmpujes() {
        return empujes;
    }

    public void reset() {
        this.movimientos = 0;
        this.empujes = 0;
        notifyObservers();
    }

    private void notifyObservers() {
        for (GameStatsObserver obs : observers) {
            obs.onStatsUpdated(movimientos, empujes);
        }
    }
}
