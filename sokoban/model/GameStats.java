package sokoban.model;

public class GameStats {

    private int movimientos;
    private int empujes;

    public void record(MoveResult result) {
        if (result.isMoved()) {
            movimientos++;
        }
        if (result.isPushed()) {
            empujes++;
        }
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
    }
}
