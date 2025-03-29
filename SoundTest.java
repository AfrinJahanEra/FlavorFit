
public class SoundTest {

    private static final String SOUND_FILE_PATH = "mixkit-software-interface-start-2574.wav";
    
    public static void main(String[] args) {
        System.out.println("Testing sound playback...");
        SoundUtils.playSound(SOUND_FILE_PATH);  // Using static method directly
        
        // Keep application running for 3 seconds to hear the sound
        try { 
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

