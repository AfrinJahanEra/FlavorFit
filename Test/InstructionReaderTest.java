package Test;

import org.junit.jupiter.api.Test;

import src.Exercise.InstructionReader;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InstructionReaderTest {

    @Test
    public void testReadInstructionsValidFile() throws Exception {
        // Create a temporary file with sample content
        String filePath = "test_instructions.txt";
        Files.writeString(Paths.get(filePath), "This is a test instruction.");
        
        String content = InstructionReader.readInstructions(filePath);
        assertEquals("This is a test instruction.", content);
        
        // Clean up
        Files.delete(Paths.get(filePath));
    }

    @Test
    public void testReadInstructionsInvalidFile() {
        String invalidFilePath = "non_existing_file.txt";
        String content = InstructionReader.readInstructions(invalidFilePath);
        assertTrue(content.startsWith("Error reading instructions:"));
    }
}
