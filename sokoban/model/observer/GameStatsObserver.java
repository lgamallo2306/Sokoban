package sokoban.model.observer;

public interface GameStatsObserver {
    void onStatsUpdated(int movimientos, int empujes);
}
