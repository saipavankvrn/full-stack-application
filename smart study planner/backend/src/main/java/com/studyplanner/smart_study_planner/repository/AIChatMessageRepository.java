package com.studyplanner.smart_study_planner.repository;

import com.studyplanner.smart_study_planner.model.AIChatMessage;
import com.studyplanner.smart_study_planner.model.AIChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIChatMessageRepository extends JpaRepository<AIChatMessage, Long> {
    List<AIChatMessage> findBySessionOrderByTimestampAsc(AIChatSession session);
}
