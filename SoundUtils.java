import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class SoundUtils {

    public static void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + soundFilePath);
                return;
            }
            
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            
            // Don't sleep here - let the sound play in the background
            // Thread.sleep(clip.getMicrosecondLength() / 1000);
            
            // Instead, add a listener to close the clip when done
            clip.addLineListener(event -> {
                if (event.getType().equals(javax.sound.sampled.LineEvent.Type.STOP)) {
                    clip.close();
                    try { audioInputStream.close(); } catch (Exception e) {}
                }
            });
            
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
            e.printStackTrace();
        }
    }
}