import sokoban.controlador.ControladorJuego;
import sokoban.modelo.Juego;
import sokoban.modelo.Tablero;
import sokoban.modelo.factory.CargadorNiveles;
import sokoban.singleton.GestorSonido;
import sokoban.vista.VistaJuego;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada. Arma el grafo de objetos (modelo, vista, controlador,
 * observadores) y muestra la ventana del juego.
 */
public class Main {

    private static final String NIVEL_INICIAL = "lvl3.txt";

    public static void main(String[] args) {
        String ruta = args.length > 0 ? args[0] : NIVEL_INICIAL;
        SwingUtilities.invokeLater(() -> iniciar(ruta));
    }

    private static void iniciar(String ruta) {
        CargadorNiveles cargador = new CargadorNiveles();
        Tablero tablero = cargador.cargar(ruta);

        Juego juego = new Juego(tablero, 1, ruta);

        VistaJuego vista = new VistaJuego();
        juego.agregarObservador(vista);
        juego.agregarObservador(GestorSonido.getInstancia());

        ControladorJuego controlador = new ControladorJuego(juego);
        vista.inicializar(controlador);
        vista.setVisible(true);

        juego.notificarEstadoInicial();
    }
}
