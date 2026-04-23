package com.studyplanner.smart_study_planner.controller;

import com.studyplanner.smart_study_planner.model.ActivitySession;
import com.studyplanner.smart_study_planner.model.User;
import com.studyplanner.smart_study_planner.repository.ActivitySessionRepository;
import com.studyplanner.smart_study_planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsRestController {

    @Autowired
    private ActivitySessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/track")
    public ResponseEntity<?> trackActivity(@RequestBody Map<String, Object> payload) {
        User user = getLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String moduleName = (String) payload.get("moduleName");
        Long duration = Long.valueOf(payload.get("duration").toString());

        ActivitySession session = new ActivitySession();
        session.setUser(user);
        session.setModuleName(moduleName);
        session.setDurationSeconds(duration);
        session.setStartTime(LocalDateTime.now());
        
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        return userRepository.findByUsername(auth.getName()).orElse(null);
    }
}
