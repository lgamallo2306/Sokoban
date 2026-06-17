package sokoban.controlador;

import sokoban.modelo.Direccion;
import sokoban.modelo.Juego;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ControladorJuego extends KeyAdapter {

    private final Juego juego;

    public ControladorJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                juego.moverJugador(Direccion.ARRIBA);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                juego.moverJugador(Direccion.ABAJO);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                juego.moverJugador(Direccion.IZQUIERDA);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                juego.moverJugador(Direccion.DERECHA);
                break;
            case KeyEvent.VK_Z:
            case KeyEvent.VK_U:
                onUndo();
                break;
            case KeyEvent.VK_R:
                onReiniciar();
                break;
            default:
                break;
        }
    }

    public void onUndo() {
        juego.deshacer();
    }

    public void onReiniciar() {
        juego.reiniciarNivel();
    }
}
