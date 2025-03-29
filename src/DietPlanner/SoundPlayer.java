package src.DietPlanner;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class SoundPlayer {

    private static final String SOUND_FILE_PATH = "mixkit-software-interface-start-2574.wav";

    public static void playSound() {
        try {
            File soundFile = new File(SOUND_FILE_PATH);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + SOUND_FILE_PATH);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType().equals(javax.sound.sampled.LineEvent.Type.STOP)) {
                    clip.close();
                    try {
                        audioInputStream.close();
                    } catch (Exception e) {
                    }
                }
            });

        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
            e.printStackTrace();
        }
    }
}