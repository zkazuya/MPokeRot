import java.io.InputStream;
import javax.sound.sampled.*;

public class SoundHelper {

    private static Clip currentClip;

    // Plug and Play
    public static void playSound(String path) {

        new Thread(() -> {
            try {
                stopSound();

                InputStream audioSrc =
                        SoundHelper.class.getResourceAsStream(path);

                if (audioSrc == null) {
                    System.err.println("Sound not found: " + path);
                    return;
                }

                AudioInputStream audio =
                        AudioSystem.getAudioInputStream(audioSrc);

                currentClip = AudioSystem.getClip();
                currentClip.open(audio);

                currentClip.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Loop
    public static void loopSound(String path) {

        new Thread(() -> {
            try {
                stopSound();

                InputStream audioSrc =
                        SoundHelper.class.getResourceAsStream(path);

                if (audioSrc == null) {
                    System.err.println("Sound not found: " + path);
                    return;
                }

                AudioInputStream audio =
                        AudioSystem.getAudioInputStream(audioSrc);

                currentClip = AudioSystem.getClip();
                currentClip.open(audio);

                currentClip.loop(Clip.LOOP_CONTINUOUSLY);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // STOP CURRENT SOUND
    public static void stopSound() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }
}