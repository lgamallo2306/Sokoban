package sokoban.modelo;

/**
 * Contadores del nivel: movimientos, empujes y usos de deshacer.
 */
public class Estadisticas {

    private int movimientos;
    private int empujes;
    private int usosUndo;

    public Estadisticas() {
    }

    public Estadisticas(int movimientos, int empujes, int usosUndo) {
        this.movimientos = movimientos;
        this.empujes = empujes;
        this.usosUndo = usosUndo;
    }

    public void registrarMovimiento() {
        movimientos++;
    }

    public void registrarEmpuje() {
        empujes++;
    }

    public void registrarUndo() {
        usosUndo++;
    }

    public int getMovimientos() {
        return movimientos;
    }

    public int getEmpujes() {
        return empujes;
    }

    public int getUsosUndo() {
        return usosUndo;
    }

    public void reiniciar() {
        movimientos = 0;
        empujes = 0;
        usosUndo = 0;
    }

    /** Copia defensiva (usada por el Memento). */
    public Estadisticas copia() {
        return new Estadisticas(movimientos, empujes, usosUndo);
    }

    public void copiarDesde(Estadisticas otra) {
        this.movimientos = otra.movimientos;
        this.empujes = otra.empujes;
        this.usosUndo = otra.usosUndo;
    }
}
