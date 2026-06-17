package sokoban.singleton;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton que centraliza la carga (perezosa y cacheada) de imagenes y sonidos.
 * Traduce claves logicas (ej. "jugador_abajo", "pared") a los sprites del pack
 * "Default size". Si un recurso no existe, devuelve null y la vista recurre a un
 * dibujo por defecto.
 */
public class GestorAssets {

    private static final String SPRITES = "Default size";

    private static final GestorAssets instancia = new GestorAssets();

    private final Map<String, String> rutas = new HashMap<>();
    private final Map<String, Image> imagenes = new HashMap<>();
    private final Map<String, Clip> sonidos = new HashMap<>();

    private GestorAssets() {
        // Terrenos
        rutas.put("piso", SPRITES + "/Ground/ground_01.png");
        rutas.put("resbaladizo", SPRITES + "/Ground/ground_06.png");
        rutas.put("destino", SPRITES + "/Environment/environment_05.png");
        rutas.put("cerrojo", SPRITES + "/Environment/environment_08.png");
        // Obstaculos
        rutas.put("pared", SPRITES + "/Blocks/block_01.png");
        rutas.put("muro", SPRITES + "/Blocks/block_06.png");
        // Jugador (por direccion; izquierda se dibuja espejando la derecha)
        rutas.put("jugador_abajo", SPRITES + "/Player/player_01.png");
        rutas.put("jugador_arriba", SPRITES + "/Player/player_09.png");
        rutas.put("jugador_derecha", SPRITES + "/Player/player_17.png");
        // Cajas
        rutas.put("caja_normal", SPRITES + "/Crates/crate_07.png");
        rutas.put("caja_fragil", SPRITES + "/Crates/crate_43.png");
        rutas.put("caja_llave", SPRITES + "/Crates/crate_04.png");
    }

    public static GestorAssets getInstancia() {
        return instancia;
    }

    public Image getImagen(String clave) {
        if (imagenes.containsKey(clave)) {
            return imagenes.get(clave);
        }
        Image img = null;
        try {
            String ruta = rutas.get(clave);
            if (ruta != null) {
                File archivo = new File(ruta);
                if (archivo.exists()) {
                    img = ImageIO.read(archivo);
                }
            }
        } catch (Exception e) {
            img = null;
        }
        imagenes.put(clave, img);
        return img;
    }

    public Clip getSonido(String clave) {
        if (sonidos.containsKey(clave)) {
            return sonidos.get(clave);
        }
        Clip clip = null;
        try {
            File archivo = new File("assets/" + clave + ".wav");
            if (archivo.exists()) {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(archivo));
            }
        } catch (Exception e) {
            clip = null;
        }
        sonidos.put(clave, clip);
        return clip;
    }
}
