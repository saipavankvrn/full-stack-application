package com.studyplanner.smart_study_planner.controller;

import com.studyplanner.smart_study_planner.model.AIChatMessage;
import com.studyplanner.smart_study_planner.model.AIChatSession;
import com.studyplanner.smart_study_planner.model.User;
import com.studyplanner.smart_study_planner.repository.AIChatMessageRepository;
import com.studyplanner.smart_study_planner.repository.AIChatSessionRepository;
import com.studyplanner.smart_study_planner.repository.UserRepository;
import com.studyplanner.smart_study_planner.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @Autowired
    private AIChatSessionRepository sessionRepository;

    @Autowired
    private AIChatMessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    // Start a new session
    @PostMapping("/new")
    public ResponseEntity<?> createNewSession() {
        User user = getLoggedInUser();
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        AIChatSession session = new AIChatSession();
        session.setUser(user);
        session.setTitle("New Conversation");
        session = sessionRepository.save(session);
        
        return ResponseEntity.ok(session);
    }

    // List sessions
    @GetMapping("/sessions")
    public ResponseEntity<?> getSessions() {
        User user = getLoggedInUser();
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        List<AIChatSession> sessions = sessionRepository.findByUserOrderByCreatedAtDesc(user);
        return ResponseEntity.ok(sessions);
    }

    // List top 10 sessions (History)
    @GetMapping("/history")
    public ResponseEntity<?> getHistorySessions() {
        User user = getLoggedInUser();
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        List<AIChatSession> sessions = sessionRepository.findTop10ByUserOrderByCreatedAtDesc(user);
        return ResponseEntity.ok(sessions);
    }

    // Load history
    @GetMapping("/sessions/{id}")
    public ResponseEntity<?> getSessionHistory(@PathVariable Long id) {
        User user = getLoggedInUser();
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        Optional<AIChatSession> optSession = sessionRepository.findById(id);
        if (!optSession.isPresent() || !optSession.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(404).body("Session not found");
        }

        List<AIChatMessage> messages = messageRepository.findBySessionOrderByTimestampAsc(optSession.get());
        return ResponseEntity.ok(messages);
    }

    // Post message
    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody Map<String, String> request) {
        User user = getLoggedInUser();
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        String userMessage = request.get("userMessage");
        String context = request.get("pageContext");
        Long sessionId = request.get("sessionId") != null ? Long.parseLong(request.get("sessionId")) : null;

        AIChatSession session;
        if (sessionId != null) {
            Optional<AIChatSession> opt = sessionRepository.findById(sessionId);
            if (opt.isPresent() && opt.get().getUser().getId().equals(user.getId())) {
                session = opt.get();
            } else {
                return ResponseEntity.status(404).body("Session not found");
            }
        } else {
            // Auto-create session if none exists
            session = new AIChatSession();
            session.setUser(user);
            session.setTitle("New Conversation");
            session = sessionRepository.save(session);
        }

        // Save User Message
        AIChatMessage userMsgEntity = new AIChatMessage();
        userMsgEntity.setSession(session);
        userMsgEntity.setRole("user");
        userMsgEntity.setContent(userMessage);
        messageRepository.save(userMsgEntity);

        // Fetch History
        List<AIChatMessage> history = messageRepository.findBySessionOrderByTimestampAsc(session);
        // Exclude the last message from history as it's passed explicitly
        if (history.size() > 0) history.remove(history.size() - 1);

        // Ask AI
        String aiResponse = aiService.askAITutor(history, userMessage, context);

        // Save AI Response
        AIChatMessage botMsgEntity = new AIChatMessage();
        botMsgEntity.setSession(session);
        botMsgEntity.setRole("assistant");
        botMsgEntity.setContent(aiResponse);
        messageRepository.save(botMsgEntity);

        // Update title if it's the first message
        if (history.isEmpty() || session.getTitle().equals("New Conversation")) {
            String newTitle = aiService.generateTitle(userMessage);
            session.setTitle(newTitle);
            sessionRepository.save(session);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("response", aiResponse);
        response.put("sessionId", session.getId());
        response.put("title", session.getTitle());

        return ResponseEntity.ok(response);
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        return userRepository.findByUsername(auth.getName()).orElse(null);
    }
}
