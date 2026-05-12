import java.io.File;
import javax.sound.sampled.*;

public class SoundHelper {

    public static void playSound(String path){

        try{

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(new File(path));

            Clip clip = AudioSystem.getClip();

            clip.open(audio);

            // auto cleanup
            clip.addLineListener(event -> {

                if(event.getType() == LineEvent.Type.STOP){

                    clip.close();
                }
            });

            clip.start();

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}