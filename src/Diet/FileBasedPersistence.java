package src.Diet;

import java.io.*;

public class FileBasedPersistence implements PlannerPersistence {
    private final String saveFile;

    public FileBasedPersistence(String saveFile) {
        this.saveFile = saveFile;
    }

    @Override
    public void save(DayPlannerState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            oos.writeObject(state);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    @Override
    public DayPlannerState load() {
        File file = new File(saveFile);
        if (!file.exists()) return null;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (DayPlannerState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}

