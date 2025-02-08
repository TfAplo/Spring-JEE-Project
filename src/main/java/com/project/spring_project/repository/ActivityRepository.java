package com.project.spring_project.repository;

import com.project.spring_project.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a " +
            "JOIN Activity_Pathology ap ON a.id_activity = ap.activity.id_activity " +
            "JOIN User_Pathology up ON ap.pathology.id_pathology = up.pathology.id_pathology " +
            "WHERE up.user.id_user = :userId")
    List<Activity> findActivitiesByUserId(@Param("userId") int userId);
    List<Activity> findByNameContaining(String name);
}