package sokoban.modelo;

import sokoban.modelo.factory.CargadorNiveles;
import sokoban.modelo.factory.FabricaElementos;
import sokoban.modelo.memento.GestorUndo;
import sokoban.modelo.memento.MementoJuego;
import sokoban.modelo.objeto.Jugador;
import sokoban.modelo.objeto.ObjetoJuego;
import sokoban.modelo.observer.EventoJuego;
import sokoban.modelo.observer.ObservadorJuego;
import sokoban.modelo.strategy.EstrategiaPuntaje;
import sokoban.modelo.strategy.PuntajeEstandar;

import java.util.ArrayList;
import java.util.List;

/**
 * Nucleo del modelo. Coordina el estado del juego, aplica las reglas de
 * movimiento y notifica a los observadores (Observer). Soporta deshacer
 * mediante Memento y calcula puntaje mediante Strategy.
 */
public class Juego {

    private Tablero tablero;
    private int nivelActual;
    private final String rutaNivel;
    private final Estadisticas stats;
    private final List<ObservadorJuego> observadores = new ArrayList<>();

    private final GestorUndo gestorUndo = new GestorUndo();
    private final EstrategiaPuntaje estrategia;
    private final CargadorNiveles cargador;
    private final FabricaElementos fabrica;

    public Juego(Tablero tablero, int nivelActual, String rutaNivel) {
        this(tablero, nivelActual, rutaNivel, new PuntajeEstandar(), new CargadorNiveles(), new FabricaElementos());
    }

    public Juego(Tablero tablero, int nivelActual, String rutaNivel,
                 EstrategiaPuntaje estrategia, CargadorNiveles cargador, FabricaElementos fabrica) {
        this.tablero = tablero;
        this.nivelActual = nivelActual;
        this.rutaNivel = rutaNivel;
        this.stats = new Estadisticas();
        this.estrategia = estrategia;
        this.cargador = cargador;
        this.fabrica = fabrica;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Estadisticas getEstadisticas() {
        return stats;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean puedeDeshacer() {
        return gestorUndo.puedeDeshacer();
    }

    public int getPuntaje() {
        return estrategia.calcular(stats);
    }

    // ===== Observer =====

    public void agregarObservador(ObservadorJuego o) {
        observadores.add(o);
    }

    private void notificar(EventoJuego e) {
        for (ObservadorJuego o : observadores) {
            o.actualizar(e);
        }
    }

    private void notificar(EventoJuego.Tipo tipo) {
        boolean victoria = tablero.esVictoria();
        EventoJuego.Tipo tipoFinal = victoria ? EventoJuego.Tipo.VICTORIA : tipo;
        notificar(new EventoJuego(tipoFinal, tablero, stats, nivelActual, victoria, getPuntaje()));
    }

    /** Notifica el estado inicial para que la vista realice el primer render. */
    public void notificarEstadoInicial() {
        notificar(new EventoJuego(EventoJuego.Tipo.INICIO, tablero, stats, nivelActual, false, getPuntaje()));
    }

    // ===== Logica de juego =====

    /**
     * Intenta mover al jugador en la direccion dada. Empuja una caja si la hay
     * y el espacio detras esta libre. Notifica el resultado.
     */
    public void moverJugador(Direccion d) {
        Jugador jugador = tablero.getJugador();
        if (jugador == null) {
            return;
        }
        Posicion origen = jugador.getPosicion();
        Posicion destino = origen.trasladar(d);
        if (!tablero.estaDentro(destino)) {
            notificar(EventoJuego.Tipo.BLOQUEADO);
            return;
        }

        Celda celdaDestino = tablero.getCelda(destino);
        ObjetoJuego ocupante = celdaDestino.getObjeto();
        boolean empujo = false;

        // Instantanea previa a cualquier modificacion (para deshacer).
        MementoJuego previo = crearMemento();

        if (ocupante != null) {
            if (ocupante.esEmpujable()) {
                if (!intentarEmpujar(ocupante, destino, d)) {
                    notificar(EventoJuego.Tipo.BLOQUEADO);
                    return;
                }
                empujo = true;
            } else if (ocupante.bloqueaPaso()) {
                notificar(EventoJuego.Tipo.BLOQUEADO);
                return;
            }
        }

        jugador.setUltimaDireccion(d);
        tablero.moverObjeto(origen, destino);
        celdaDestino.getTerreno().alEntrar(jugador, tablero);

        stats.registrarMovimiento();
        if (empujo) {
            stats.registrarEmpuje();
        }
        gestorUndo.guardar(previo);

        notificar(empujo ? EventoJuego.Tipo.EMPUJE : EventoJuego.Tipo.MOVIMIENTO);
    }

    /** Empuja la caja ubicada en posCaja hacia la direccion d. Retorna si se pudo. */
    private boolean intentarEmpujar(ObjetoJuego caja, Posicion posCaja, Direccion d) {
        Posicion destinoCaja = posCaja.trasladar(d);
        if (!tablero.puedeMoverObjetoA(destinoCaja)) {
            return false;
        }
        caja.setUltimaDireccion(d);
        tablero.moverObjeto(posCaja, destinoCaja);
        ((sokoban.modelo.objeto.caja.Caja) caja).alSerEmpujada(tablero);
        // Si la caja sobrevivio, el terreno de llegada reacciona (deslizamiento, cerrojo...).
        if (tablero.getCelda(destinoCaja).getObjeto() == caja) {
            tablero.getCelda(destinoCaja).getTerreno().alEntrar(caja, tablero);
        }
        return true;
    }

    // ===== Memento =====

    public MementoJuego crearMemento() {
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();
        char[][] capaObjetos = new char[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                ObjetoJuego o = tablero.getCelda(new Posicion(f, c)).getObjeto();
                capaObjetos[f][c] = (o == null) ? ' ' : o.getSimbolo();
            }
        }
        return new MementoJuego(capaObjetos, stats.copia());
    }

    public void restaurar(MementoJuego m) {
        char[][] capa = m.getEstadoTablero();
        for (int f = 0; f < capa.length; f++) {
            for (int c = 0; c < capa[f].length; c++) {
                Posicion p = new Posicion(f, c);
                ObjetoJuego objeto = fabrica.crearObjeto(capa[f][c], p);
                tablero.getCelda(p).setObjeto(objeto);
            }
        }
        tablero.recalcularJugador();
        tablero.vincularCerrojos();
        stats.copiarDesde(m.getEstadoStats());
    }

    public void deshacer() {
        MementoJuego m = gestorUndo.deshacer();
        if (m == null) {
            notificar(EventoJuego.Tipo.BLOQUEADO);
            return;
        }
        restaurar(m);
        stats.registrarUndo();
        notificar(EventoJuego.Tipo.DESHACER);
    }

    public void reiniciarNivel() {
        this.tablero = cargador.cargar(rutaNivel);
        stats.reiniciar();
        gestorUndo.limpiar();
        notificar(EventoJuego.Tipo.REINICIO);
    }
}
