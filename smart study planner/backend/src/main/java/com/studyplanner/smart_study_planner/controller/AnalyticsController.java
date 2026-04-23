package com.studyplanner.smart_study_planner.controller;

import com.studyplanner.smart_study_planner.model.User;
import com.studyplanner.smart_study_planner.repository.ActivitySessionRepository;
import com.studyplanner.smart_study_planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnalyticsController {

    @Autowired
    private ActivitySessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/analytics")
    public String viewAnalytics(Model model) {
        User user = getLoggedInUser();
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        // Get total study time today
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Long totalSeconds = sessionRepository.getTotalStudyTimeToday(user, startOfDay);
        if (totalSeconds == null) totalSeconds = 0L;
        
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        String formattedTime = String.format("%02dh %02dm", hours, minutes);
        
        // Get aggregated data for pie chart
        List<Object[]> aggregatedData = sessionRepository.getAggregatedTimeByModule(user);
        Map<String, Long> chartData = new HashMap<>(); // using map for easier serialization later if needed
        for (Object[] row : aggregatedData) {
            chartData.put((String) row[0], ((Number) row[1]).longValue());
        }

        model.addAttribute("totalStudyTimeToday", formattedTime);
        model.addAttribute("chartData", chartData);
        
        return "analytics";
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        return userRepository.findByUsername(auth.getName()).orElse(null);
    }
}
