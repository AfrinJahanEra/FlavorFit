package src.Diet;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PersistentFoodDatabase implements FoodLookup, FoodWriter, FoodPersistence {
    private final Map<String, NutritionalInfo> database = new HashMap<>();
    private final String binaryFilename;
    private final String textFilename;
    private final Path dataDirectory;

    public PersistentFoodDatabase(String binaryFilename, String textFilename) throws FoodDatabaseException {
        this.binaryFilename = binaryFilename;
        this.textFilename = textFilename;
        this.dataDirectory = Paths.get("data");

        try {
            initializeDatabase();
        } catch (FoodDatabaseException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            throw new FoodDatabaseException("Failed to initialize food database", e);
        }
    }

    private void initializeDatabase() throws FoodDatabaseException {
        try {
            // Create data directory if it doesn't exist
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            ensureDefaultFoodsExist();
            loadFromFiles();
        } catch (IOException e) {
            throw new FoodDatabaseException("Could not initialize data directory", e);
        }
    }

    private void ensureDefaultFoodsExist() throws FoodDatabaseException {
        Path textFile = dataDirectory.resolve(textFilename);

        if (!Files.exists(textFile)) {
            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(textFile))) {
                writer.println("# Default foods database - format: name:calories:protein(g):carbs(g):fat(g)");
                writer.println("apple:95:0.5:25:0.3");
                writer.println("banana:105:1.3:27:0.4");
                writer.println("chicken breast:165:31:0:3.6");
                writer.println("rice:130:2.7:28:0.3");
                writer.println("whole wheat bread:69:3.6:12:0.9");
                writer.println("milk:103:8:12:2.4");
            } catch (IOException e) {
                throw new FoodDatabaseException("Could not create default foods file", e);
            }
        }
    }

    private void loadFromFiles() throws FoodDatabaseException {
        try {
            loadFromBinaryFile();
            loadFromTextFile();
        } catch (IOException e) {
            throw new FoodDatabaseException("Failed to load food database", e);
        }
    }

    private void loadFromBinaryFile() throws IOException {
        Path binaryFile = dataDirectory.resolve(binaryFilename);
        if (!Files.exists(binaryFile)) return;
    
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(binaryFile))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, NutritionalInfo> loaded = (Map<String, NutritionalInfo>) obj;
                database.putAll(loaded);
            }
        } catch (ClassNotFoundException | InvalidClassException e) {
            System.err.println("Warning: Corrupted binary food database, starting fresh");
            // Delete corrupted file
            Files.deleteIfExists(binaryFile);
        } catch (StreamCorruptedException e) {
            System.err.println("Warning: Invalid binary food database format, starting fresh");
            Files.deleteIfExists(binaryFile);
        }
    }

    private void loadFromTextFile() throws IOException {
        Path textFile = dataDirectory.resolve(textFilename);
        if (!Files.exists(textFile)) {
            throw new FileNotFoundException("Default foods file not found: " + textFile);
        }

        try (BufferedReader reader = Files.newBufferedReader(textFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#"))
                    continue;

                try {
                    String[] parts = line.split(":");
                    if (parts.length != 5) {
                        System.err.println("Skipping malformed line: " + line);
                        continue;
                    }

                    String name = parts[0].trim();
                    int calories = Integer.parseInt(parts[1].trim());
                    double protein = Double.parseDouble(parts[2].trim());
                    double carbs = Double.parseDouble(parts[3].trim());
                    double fat = Double.parseDouble(parts[4].trim());

                    database.putIfAbsent(name.toLowerCase(),
                            new NutritionalInfo(calories, protein, carbs, fat));
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid nutrition data in line: " + line);
                }
            }
        }
    }

    @Override
    public Optional<NutritionalInfo> lookupFood(String foodName) {
        Objects.requireNonNull(foodName, "Food name cannot be null");
        return Optional.ofNullable(database.get(foodName.toLowerCase()));
    }

    @Override
    public void addFood(String foodName, NutritionalInfo info) throws FoodDatabaseException {
        Objects.requireNonNull(foodName, "Food name cannot be null");
        Objects.requireNonNull(info, "Nutritional info cannot be null");

        try {
            database.put(foodName.toLowerCase(), info);
            saveToFile();
        } catch (Exception e) {
            throw new FoodDatabaseException("Failed to add food: " + foodName, e);
        }
    }

    @Override

    public void saveToFile() throws FoodDatabaseException {
        Path binaryFile = dataDirectory.resolve(binaryFilename);

        // Create temp file first
        Path tempFile = dataDirectory.resolve(binaryFilename + ".tmp");

        try (ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(tempFile, StandardOpenOption.CREATE))) {
            oos.writeObject(database);

            // Only replace original if save succeeded
            Files.move(tempFile, binaryFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            try {
                Files.deleteIfExists(tempFile); // Clean up temp file
            } catch (IOException ex) {
                // Ignore cleanup failure
            }
            throw new FoodDatabaseException("Failed to save food database", e);
        }
    }

    @Override
    public void loadFromFile() throws FoodDatabaseException, IOException {
        loadFromFiles();
    }

    // Custom exception for food database operations
    public static class FoodDatabaseException extends Exception {
        public FoodDatabaseException(String message) {
            super(message);
        }

        public FoodDatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}