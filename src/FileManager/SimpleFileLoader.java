package src.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import src.Interface.FileLoader;

public class SimpleFileLoader implements FileLoader<String> {
    @Override
    public List<String> loadFromFile(String filePath) throws IOException {
        try {
            validateFile(filePath);
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Warning: Could not load file - " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void validateFile(String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IOException("File path cannot be null or empty");
        }

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File not found at: " + path.toAbsolutePath());
        }
        if (!Files.isReadable(path)) {
            throw new IOException("No read permissions for: " + path);
        }
    }
}