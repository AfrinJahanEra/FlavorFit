// QnAService.java
package src.VirtualNutritionist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.User.User;

public class QnAService {
    private final Map<String, List<String>> questionAnswerMap;
    private final List<String> allQuestions;
    
    public QnAService() {
        this.questionAnswerMap = new HashMap<>();
        this.allQuestions = new ArrayList<>();
        loadQnAFromFile("src\\VirtualNutritionist\\qna.txt");
    }

    private void loadQnAFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String question = parts[0].toLowerCase().trim();
                    String condition = parts[1].toLowerCase().trim();
                    String answer = parts[2].trim();
                    
                    // Store all questions for matching
                    if (!allQuestions.contains(question)) {
                        allQuestions.add(question);
                    }
                    
                    // Create a key combining question and condition
                    String key = question + "|" + condition;
                    questionAnswerMap.putIfAbsent(key, new ArrayList<>());
                    questionAnswerMap.get(key).add(answer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading Q&A file: " + e.getMessage());
        }
    }

    public String getResponse(String question, User user) {
        String lowerQuestion = question.toLowerCase().trim();
        
        // First try to find exact matches with user conditions
        List<String> possibleAnswers = new ArrayList<>();
        
        // Check for answers specific to user's medical conditions
        for (String condition : user.getMedicalConditions()) {
            String key = lowerQuestion + "|" + condition.toLowerCase();
            if (questionAnswerMap.containsKey(key)) {
                possibleAnswers.addAll(questionAnswerMap.get(key));
            }
        }
        
        // Check for general answers (marked with "all")
        String generalKey = lowerQuestion + "|all";
        if (questionAnswerMap.containsKey(generalKey)) {
            possibleAnswers.addAll(questionAnswerMap.get(generalKey));
        }
        
        // If we found specific answers, return them
        if (!possibleAnswers.isEmpty()) {
            return String.join("\n", possibleAnswers);
        }
        
        // If no exact match, try to find similar questions
        String bestMatch = findBestQuestionMatch(lowerQuestion);
        if (bestMatch != null) {
            // Try again with the matched question
            return getResponse(bestMatch, user);
        }
        
        return "I don't have an answer for that question. Please consult a nutritionist for personalized advice.";
    }
    
    private String findBestQuestionMatch(String userQuestion) {
        String bestMatch = null;
        int bestScore = 0;
        
        for (String storedQuestion : allQuestions) {
            int score = calculateMatchScore(userQuestion, storedQuestion);
            if (score > bestScore) {
                bestScore = score;
                bestMatch = storedQuestion;
            }
        }
        
        // Only return a match if it's reasonably good
        return bestScore > 3 ? bestMatch : null;
    }
    
    private int calculateMatchScore(String question1, String question2) {
        // Simple string matching algorithm
        // You could replace this with more sophisticated NLP techniques
        
        String[] words1 = question1.split("\\s+");
        String[] words2 = question2.split("\\s+");
        
        int score = 0;
        for (String word1 : words1) {
            for (String word2 : words2) {
                if (word1.equals(word2)) {
                    score++;
                }
            }
        }
        
        return score;
    }
}