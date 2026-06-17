package sokoban.vista;

import sokoban.modelo.Celda;
import sokoban.modelo.Direccion;
import sokoban.modelo.Posicion;
import sokoban.modelo.Tablero;
import sokoban.modelo.objeto.Jugador;
import sokoban.modelo.objeto.Muro;
import sokoban.modelo.objeto.ObjetoJuego;
import sokoban.modelo.objeto.Pared;
import sokoban.modelo.objeto.caja.CajaFragil;
import sokoban.modelo.objeto.caja.CajaLlave;
import sokoban.modelo.objeto.caja.CajaNormal;
import sokoban.modelo.terreno.CasillaDestino;
import sokoban.modelo.terreno.CasilleroCerrojo;
import sokoban.modelo.terreno.Terreno;
import sokoban.modelo.terreno.TerrenoResbaladizo;
import sokoban.singleton.GestorAssets;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Vista del tablero. Dibuja terrenos y objetos con los sprites del GestorAssets.
 * Si un sprite no esta disponible, recurre a figuras de color por defecto.
 */
public class PanelTablero extends JPanel {

    private static final int CELDA = 64;

    private Tablero tablero;

    public PanelTablero() {
        setBackground(new Color(30, 30, 40));
    }

    /** Recibe el tablero a renderizar y solicita el repintado. */
    public void render(Tablero t) {
        this.tablero = t;
        if (t != null) {
            setPreferredSize(new Dimension(t.getColumnas() * CELDA, t.getFilas() * CELDA));
            revalidate();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tablero == null) {
            return;
        }
        for (int f = 0; f < tablero.getFilas(); f++) {
            for (int c = 0; c < tablero.getColumnas(); c++) {
                Celda celda = tablero.getCelda(new Posicion(f, c));
                int x = c * CELDA;
                int y = f * CELDA;
                dibujarTerreno(g, celda.getTerreno(), x, y);
                dibujarObjeto(g, celda.getObjeto(), x, y);
            }
        }
    }

    // ===== Terreno =====

    private void dibujarTerreno(Graphics g, Terreno terreno, int x, int y) {
        // Base: piso o hielo.
        String baseClave = (terreno instanceof TerrenoResbaladizo) ? "resbaladizo" : "piso";
        Image base = GestorAssets.getInstancia().getImagen(baseClave);
        if (base != null) {
            g.drawImage(base, x, y, CELDA, CELDA, this);
        } else {
            g.setColor(colorTerreno(terreno));
            g.fillRect(x, y, CELDA, CELDA);
            g.setColor(new Color(0, 0, 0, 40));
            g.drawRect(x, y, CELDA, CELDA);
        }

        // Marcadores sobre el piso.
        if (terreno instanceof CasillaDestino) {
            dibujarMarcador(g, "destino", new Color(220, 80, 80), x, y);
        } else if (terreno instanceof CasilleroCerrojo) {
            dibujarMarcador(g, "cerrojo", new Color(70, 140, 230), x, y);
        }
    }

    private void dibujarMarcador(Graphics g, String clave, Color fallback, int x, int y) {
        Image marca = GestorAssets.getInstancia().getImagen(clave);
        if (marca != null) {
            g.drawImage(marca, x, y, CELDA, CELDA, this);
        } else {
            g.setColor(fallback);
            g.fillOval(x + CELDA / 3, y + CELDA / 3, CELDA / 3, CELDA / 3);
        }
    }

    // ===== Objetos =====

    private void dibujarObjeto(Graphics g, ObjetoJuego objeto, int x, int y) {
        if (objeto == null) {
            return;
        }
        if (objeto instanceof Jugador) {
            dibujarJugador(g, (Jugador) objeto, x, y);
            return;
        }

        String clave = claveObjeto(objeto);
        Image img = GestorAssets.getInstancia().getImagen(clave);
        if (img != null) {
            g.drawImage(img, x, y, CELDA, CELDA, this);
        } else {
            dibujarObjetoPorDefecto(g, objeto, x, y);
        }
    }

    private void dibujarJugador(Graphics g, Jugador jugador, int x, int y) {
        Direccion d = jugador.getUltimaDireccion();
        String clave = "jugador_abajo";
        boolean espejar = false;
        if (d == Direccion.ARRIBA) {
            clave = "jugador_arriba";
        } else if (d == Direccion.DERECHA) {
            clave = "jugador_derecha";
        } else if (d == Direccion.IZQUIERDA) {
            clave = "jugador_derecha";
            espejar = true;
        }
        Image img = GestorAssets.getInstancia().getImagen(clave);
        if (img == null) {
            g.setColor(new Color(70, 140, 230));
            g.fillOval(x + 8, y + 8, CELDA - 16, CELDA - 16);
            return;
        }
        if (espejar) {
            // Espeja horizontalmente para mirar a la izquierda.
            g.drawImage(img, x + CELDA, y, -CELDA, CELDA, this);
        } else {
            g.drawImage(img, x, y, CELDA, CELDA, this);
        }
    }

    private String claveObjeto(ObjetoJuego objeto) {
        if (objeto instanceof Pared) return "pared";
        if (objeto instanceof Muro) return "muro";
        if (objeto instanceof CajaFragil) return "caja_fragil";
        if (objeto instanceof CajaLlave) return "caja_llave";
        if (objeto instanceof CajaNormal) return "caja_normal";
        return "desconocido";
    }

    // ===== Fallbacks de color (sin sprites) =====

    private void dibujarObjetoPorDefecto(Graphics g, ObjetoJuego objeto, int x, int y) {
        int m = 6;
        if (objeto instanceof Pared) {
            g.setColor(new Color(90, 90, 110));
            g.fillRect(x, y, CELDA, CELDA);
        } else if (objeto instanceof Muro) {
            g.setColor(new Color(150, 90, 40));
            g.fillRect(x + 2, y + 2, CELDA - 4, CELDA - 4);
        } else if (objeto instanceof CajaNormal) {
            dibujarCaja(g, x, y, new Color(200, 160, 70));
        } else if (objeto instanceof CajaFragil) {
            dibujarCaja(g, x, y, new Color(180, 120, 120));
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x + m, y + m, x + CELDA - m, y + CELDA - m);
            g.drawLine(x + CELDA - m, y + m, x + m, y + CELDA - m);
        } else if (objeto instanceof CajaLlave) {
            dibujarCaja(g, x, y, new Color(220, 200, 60));
        }
    }

    private void dibujarCaja(Graphics g, int x, int y, Color color) {
        int m = 5;
        g.setColor(color);
        g.fillRect(x + m, y + m, CELDA - 2 * m, CELDA - 2 * m);
        g.setColor(color.darker());
        g.drawRect(x + m, y + m, CELDA - 2 * m, CELDA - 2 * m);
    }

    private Color colorTerreno(Terreno terreno) {
        if (terreno instanceof TerrenoResbaladizo) {
            return new Color(150, 210, 230);
        }
        if (terreno instanceof CasilleroCerrojo) {
            return new Color(120, 80, 60);
        }
        if (terreno instanceof CasillaDestino) {
            return new Color(70, 70, 90);
        }
        return new Color(50, 50, 65);
    }
}
