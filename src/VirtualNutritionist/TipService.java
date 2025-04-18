package src.VirtualNutritionist;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import src.FileManager.SimpleFileLoader;
import src.Interface.FileLoader;

public class TipService {
    private final List<String> tips;

    public TipService(String tipsFilePath) throws IOException {
        FileLoader<String> fileLoader = new SimpleFileLoader();
        this.tips = fileLoader.loadFromFile(tipsFilePath);
    }

    public List<String> getTipsForCondition(String medicalCondition) {
        if (medicalCondition == null || medicalCondition.trim().isEmpty()) {
            return List.of();
        }

        final String conditionLower = medicalCondition.toLowerCase();
        return tips.stream()
                .filter(tip -> tip != null && tip.toLowerCase().contains(conditionLower))
                .collect(Collectors.toList());
    }
}