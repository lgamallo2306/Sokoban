package sokoban.modelo.memento;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Caretaker del patron Memento. Guarda el historial de estados (hasta 15)
 * y limita los deshacer consecutivos.
 */
public class GestorUndo {

    private static final int MAX_HISTORIAL = 15;
    private static final int MAX_USOS_CONSECUTIVOS = 3;

    private final Deque<MementoJuego> historial = new ArrayDeque<>();
    private int usosConsecutivos;

    /** Guarda un nuevo estado; un movimiento real reinicia el contador de undos. */
    public void guardar(MementoJuego m) {
        historial.push(m);
        while (historial.size() > MAX_HISTORIAL) {
            historial.removeLast();
        }
        usosConsecutivos = 0;
    }

    /** Devuelve el ultimo estado guardado o null si no se puede deshacer. */
    public MementoJuego deshacer() {
        if (historial.isEmpty() || usosConsecutivos >= MAX_USOS_CONSECUTIVOS) {
            return null;
        }
        usosConsecutivos++;
        return historial.pop();
    }

    public boolean puedeDeshacer() {
        return !historial.isEmpty() && usosConsecutivos < MAX_USOS_CONSECUTIVOS;
    }

    public void limpiar() {
        historial.clear();
        usosConsecutivos = 0;
    }
}
