package src.FileManager;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InstructionReader implements FileLoader<String> {
    @Override
    public List<String> loadFromFile(String filePath) throws IOException {
        validateFile(filePath);
        return Files.readAllLines(Paths.get(filePath));
    }

    @Override
    public void validateFile(String filePath) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException("Instruction file does not exist: " + filePath);
        }
    }

    // Additional method for backward compatibility
    public String readInstructions(String filePath) {
        try {
            return String.join("\n", loadFromFile(filePath));
        } catch (Exception e) {
            return "Error reading instructions: " + e.getMessage();
        }
    }
}