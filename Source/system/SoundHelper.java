package system;
import java.io.File;
import javax.sound.sampled.*;

public class SoundHelper {

    public static void playSound(String path){
        // WRAP AUDIO IN A BACKGROUND THREAD
        new Thread(() -> {
            try {
                AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                // AUTO CLEAN UP
                clip.addLineListener(event -> {
                    if(event.getType() == LineEvent.Type.STOP){
                        clip.close();
                    }
                });
                clip.start();
            } catch(Exception e) {
                // SILENTLY IGNORE MISSING FILES INSTEAD OF PRINTING
            }
        }).start();
    }
}