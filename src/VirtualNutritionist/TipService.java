package src.VirtualNutritionist;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TipService {
    private List<String> tips;

    public TipService(String tipsFilePath) throws IOException {
        this.tips = FileLoader.loadFile(tipsFilePath);
    }

    public List<String> getTipsForCondition(String medicalCondition) {
        return tips.stream()
                   .filter(tip -> tip.toLowerCase().contains(medicalCondition.toLowerCase()))
                   .collect(Collectors.toList());
    }
}
