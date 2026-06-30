import sokoban.controlador.ControladorJuego;
import sokoban.modelo.Juego;
import sokoban.modelo.Tablero;
import sokoban.modelo.cronometro.CronometroNivel;
import sokoban.modelo.factory.CargadorNiveles;
import sokoban.modelo.factory.FabricaElementos;
import sokoban.modelo.strategy.PuntajeConTiempo;
import sokoban.modelo.strategy.PuntajeEstandar;
import sokoban.singleton.GestorSonido;
import sokoban.vista.VistaJuego;

import javax.swing.SwingUtilities;
import java.util.Arrays;
import java.util.List;

/**
 * Punto de entrada. Arma el grafo de objetos (modelo, vista, controlador,
 * observadores) y muestra la ventana del juego.
 */
public class Main {

    private static final List<String> NIVELES = Arrays.asList(
            "lvl1.txt",   // Tutorial: 1 caja, empuje directo
            "lvl3.txt",   // Clasico: 2 cajas, 2 destinos
            "lvl4.txt",   // Hielo: cajas que se deslizan hasta el destino
            "lvl5.txt",   // Llave/cerrojo: abrir paso para llegar al destino
            "lvl6.txt",   // Clasico: 3 cajas, navegacion mas compleja
            "lvl2.txt"    // Desafio final: mezcla de todos los mecanismos
    );

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> iniciar(NIVELES));
    }

    private static void iniciar(List<String> niveles) {
        CargadorNiveles cargador = new CargadorNiveles();
        Tablero tablero = cargador.cargar(niveles.get(0));

        VistaJuego vista = new VistaJuego();

        CronometroNivel cronometro = new CronometroNivel(vista::actualizarTiempo);

        Juego juego = new Juego(tablero, niveles, 0,
                new PuntajeConTiempo(new PuntajeEstandar(), cronometro),
                cargador, new FabricaElementos());

        juego.agregarObservador(vista);
        juego.agregarObservador(GestorSonido.getInstancia());
        juego.agregarObservador(cronometro);

        vista.setCronometro(cronometro);

        ControladorJuego controlador = new ControladorJuego(juego);
        vista.inicializar(controlador);
        vista.setVisible(true);

        juego.notificarEstadoInicial();
    }
}
