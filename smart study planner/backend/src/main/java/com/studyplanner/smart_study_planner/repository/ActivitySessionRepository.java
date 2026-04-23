package com.studyplanner.smart_study_planner.repository;

import com.studyplanner.smart_study_planner.model.ActivitySession;
import com.studyplanner.smart_study_planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivitySessionRepository extends JpaRepository<ActivitySession, Long> {
    
    @Query("SELECT a.moduleName as moduleName, SUM(a.durationSeconds) as totalDuration FROM ActivitySession a WHERE a.user = :user GROUP BY a.moduleName")
    List<Object[]> getAggregatedTimeByModule(@Param("user") User user);
    
    @Query("SELECT SUM(a.durationSeconds) FROM ActivitySession a WHERE a.user = :user AND a.startTime >= :startOfDay")
    Long getTotalStudyTimeToday(@Param("user") User user, @Param("startOfDay") LocalDateTime startOfDay);
}
