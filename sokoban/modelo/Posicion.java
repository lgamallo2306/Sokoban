package sokoban.modelo;

import java.util.Objects;

/**
 * Value Object inmutable que representa una coordenada (fila, columna) del tablero.
 */
public final class Posicion {

    private final int fila;
    private final int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    /** Retorna una nueva posicion desplazada en la direccion indicada. */
    public Posicion trasladar(Direccion d) {
        return new Posicion(fila + d.getDeltaFila(), columna + d.getDeltaColumna());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Posicion)) return false;
        Posicion p = (Posicion) o;
        return fila == p.fila && columna == p.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }

    @Override
    public String toString() {
        return "(" + fila + ", " + columna + ")";
    }
}
