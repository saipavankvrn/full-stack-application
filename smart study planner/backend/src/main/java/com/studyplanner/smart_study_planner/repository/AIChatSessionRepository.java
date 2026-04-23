package com.studyplanner.smart_study_planner.repository;

import com.studyplanner.smart_study_planner.model.AIChatSession;
import com.studyplanner.smart_study_planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIChatSessionRepository extends JpaRepository<AIChatSession, Long> {
    List<AIChatSession> findByUserOrderByCreatedAtDesc(User user);
    List<AIChatSession> findTop10ByUserOrderByCreatedAtDesc(User user);
}
