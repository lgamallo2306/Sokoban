package sokoban.modelo.factory;

import sokoban.modelo.Celda;
import sokoban.modelo.Posicion;
import sokoban.modelo.Tablero;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory de niveles: lee un archivo .txt y construye el Tablero (Creator).
 */
public class CargadorNiveles {

    private final FabricaElementos fabrica;

    public CargadorNiveles() {
        this(new FabricaElementos());
    }

    public CargadorNiveles(FabricaElementos fabrica) {
        this.fabrica = fabrica;
    }

    public Tablero cargar(String archivoTxt) {
        List<String> lineas = leerLineas(archivoTxt);
        int filas = lineas.size();
        int columnas = lineas.stream().mapToInt(String::length).max().orElse(0);

        Celda[][] celdas = new Celda[filas][columnas];
        for (int f = 0; f < filas; f++) {
            String linea = lineas.get(f);
            for (int c = 0; c < columnas; c++) {
                char simbolo = c < linea.length() ? linea.charAt(c) : ' ';
                ElementoCreado elemento = fabrica.crearElemento(simbolo, new Posicion(f, c));
                celdas[f][c] = new Celda(elemento.getTerreno(), elemento.getObjeto());
            }
        }

        Tablero tablero = new Tablero(celdas);
        tablero.vincularCerrojos();
        return tablero;
    }

    private List<String> leerLineas(String path) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el nivel: " + path, e);
        }
        return lineas;
    }
}
