package src.Exercise;

import java.nio.file.Files;
import java.nio.file.Paths;

public class InstructionReader {
    public static String readInstructions(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (Exception e) {
            return "Error reading instructions: " + e.getMessage();
        }
    }
}