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
        rutas.put("pared", SPRITES + "/Blocks/block_05.png");
        rutas.put("muro", SPRITES + "/Blocks/block_06.png");
        // Jugador (por direccion; izquierda se dibuja espejando la derecha)
        rutas.put("jugador_abajo", SPRITES + "/Player/player_23.png");
        rutas.put("jugador_arriba", SPRITES + "/Player/player_09.png");
        rutas.put("jugador_derecha", SPRITES + "/Player/player_17.png");
        // Cajas
        rutas.put("caja_normal", SPRITES + "/Crates/crate_07.png");
        rutas.put("caja_fragil", SPRITES + "/Crates/crate_20.png");
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
            } else {
                clip = generarClip(clave);
            }
        } catch (Exception e) {
            clip = null;
        }
        sonidos.put(clave, clip);
        return clip;
    }

    private Clip generarClip(String clave) {
        double[] freqs;
        int[]    durMs;
        switch (clave) {
            case "movimiento": freqs = new double[]{600};           durMs = new int[]{60};           break;
            case "empuje":     freqs = new double[]{180};           durMs = new int[]{110};          break;
            case "deshacer":   freqs = new double[]{400, 280};      durMs = new int[]{80, 100};      break;
            case "reinicio":   freqs = new double[]{440, 550};      durMs = new int[]{100, 120};     break;
            case "victoria":   freqs = new double[]{523, 659, 784}; durMs = new int[]{130, 130, 220}; break;
            case "bloqueado":  freqs = new double[]{130};           durMs = new int[]{80};           break;
            default: return null;
        }
        return sintetizar(freqs, durMs);
    }

    private Clip sintetizar(double[] freqs, int[] durMs) {
        try {
            int sampleRate = 44100;
            int totalBytes = 0;
            for (int d : durMs) totalBytes += (sampleRate * d / 1000) * 2;

            byte[] buf = new byte[totalBytes];
            int pos = 0;
            for (int i = 0; i < freqs.length; i++) {
                int samples = sampleRate * durMs[i] / 1000;
                for (int s = 0; s < samples; s++) {
                    double t = (double) s / sampleRate;
                    // envelope suave para evitar clicks al inicio y final
                    double env = Math.min(s / 50.0, 1.0) * Math.min((samples - s) / 50.0, 1.0);
                    short val = (short) (Short.MAX_VALUE * 0.4 * env * Math.sin(2 * Math.PI * freqs[i] * t));
                    buf[pos++] = (byte) (val & 0xFF);
                    buf[pos++] = (byte) ((val >> 8) & 0xFF);
                }
            }

            javax.sound.sampled.AudioFormat fmt = new javax.sound.sampled.AudioFormat(44100, 16, 1, true, false);
            Clip clip = AudioSystem.getClip();
            clip.open(fmt, buf, 0, buf.length);
            return clip;
        } catch (Exception e) {
            return null;
        }
    }
}
