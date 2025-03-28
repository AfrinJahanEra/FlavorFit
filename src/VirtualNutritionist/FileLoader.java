package src.VirtualNutritionist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileLoader {
    public static List<String> loadFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }
}
