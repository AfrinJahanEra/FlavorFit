package src.VirtualNutritionist;

import java.util.HashMap;
import java.util.Map;
public class QnAService {
    private final Map<String, String> responseMap;
    public QnAService() {
        this.responseMap = new HashMap<>();
        responseMap.put("what should i eat for breakfast", "Consider a protein-rich breakfast.");
        responseMap.put("what is a healthy dinner", "Try a balanced meal with vegetables and lean protein.");
    }
    public String getResponse(String question) {
        return responseMap.getOrDefault(question.toLowerCase(), "I don't have an answer for that.");
    }
}
