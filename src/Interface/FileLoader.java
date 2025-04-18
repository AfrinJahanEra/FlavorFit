package src.Interface;

import java.io.IOException;
import java.util.List;

public interface FileLoader<T> {
    List<T> loadFromFile(String filePath) throws IOException;
    void validateFile(String filePath) throws IOException;
}