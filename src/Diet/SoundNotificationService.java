package src.Diet;

import javax.sound.sampled.*;

public class SoundNotificationService implements NotificationService {
    @Override
    public void playSound() {
        try {
            int duration = 1000;
            int hz = 440;
            float sampleRate = 44100;
            int numSamples = (int) (duration * sampleRate / 1000);
            byte[] buffer = new byte[numSamples];
            double angle = 2.0 * Math.PI * hz / sampleRate;

            for (int i = 0; i < numSamples; i++) {
                float value = (float) Math.sin(angle * i);
                buffer[i] = (byte) (value * 127);
            }

            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            sdl.write(buffer, 0, buffer.length);
            sdl.drain();
            sdl.close();
        } catch (LineUnavailableException e) {
            System.out.println("Audio error: " + e.getMessage());
        }
    }

    @Override
    public void showVisualAlert(String message) {
        System.out.println("\u001B[31mALARM: " + message + "\u001B[0m");
    }
}