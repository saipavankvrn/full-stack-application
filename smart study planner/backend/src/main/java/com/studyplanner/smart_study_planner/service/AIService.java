package com.studyplanner.smart_study_planner.service;

import com.studyplanner.smart_study_planner.model.AIChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    @Value("${mistral.api.key}")
    private String apiKey;

    @Value("${mistral.api.url}")
    private String apiUrl;

    @Value("${mistral.model}")
    private String model;

    @Autowired
    private RestTemplate restTemplate;

    public String askAITutor(List<AIChatMessage> history, String userMessage, String context) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        
        List<Map<String, String>> messages = new ArrayList<>();
        
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful student tutor. Use the provided page context to answer the user's question accurately. Keep answers concise, and format them nicely in markdown.");
        messages.add(systemMessage);

        // Add history
        if (history != null) {
            for (AIChatMessage msg : history) {
                Map<String, String> hm = new HashMap<>();
                hm.put("role", msg.getRole());
                hm.put("content", msg.getContent());
                messages.add(hm);
            }
        }

        // Add latest message with context
        Map<String, String> latestMessage = new HashMap<>();
        latestMessage.put("role", "user");
        String finalPrompt = "Here is the context of the page I am currently looking at:\n" + context + "\n\nMy question is: " + userMessage;
        latestMessage.put("content", finalPrompt);
        messages.add(latestMessage);

        requestBody.put("messages", messages);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        return executeCall(entity);
    }

    public String generateTitle(String firstMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> sysMessage = new HashMap<>();
        sysMessage.put("role", "system");
        sysMessage.put("content", "Generate a concise 3-word title summarizing this query.");
        messages.add(sysMessage);

        Map<String, String> usrMessage = new HashMap<>();
        usrMessage.put("role", "user");
        usrMessage.put("content", firstMessage);
        messages.add(usrMessage);

        requestBody.put("messages", messages);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String title = executeCall(entity);
        return title.replace("\"", "").trim(); // Clean up response
    }

    private String executeCall(HttpEntity<Map<String, Object>> entity) {
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Could not reach the AI Tutor at this moment. Details: " + e.getMessage();
        }
        return "Sorry, I couldn't generate a response.";
    }
}
