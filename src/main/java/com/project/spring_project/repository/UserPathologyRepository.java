package com.project.spring_project.repository;

import com.project.spring_project.entity.Pathology;
import com.project.spring_project.entity.User;
import com.project.spring_project.entity.User_Pathology;
import com.project.spring_project.entity.User_PathologyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserPathologyRepository extends JpaRepository<User_Pathology, User_PathologyId> {
    List<User_Pathology> findByUserId(int userId);
    @Query("SELECT CASE WHEN COUNT(up) > 0 THEN true ELSE false END " +
            "FROM User_Pathology up " +
            "WHERE up.user.id_user = :userId " +
            "AND up.pathology.id_pathology = :pathologyId")
    boolean existsByUserAndPathology(@Param("userId") int userId,
                                     @Param("pathologyId") int pathologyId);


    @Query("SELECT up FROM User_Pathology up " +
            "WHERE up.user.id_user = :userId " +
            "AND up.pathology.id_pathology = :pathologyId")
    Optional<User_Pathology> findByUserAndPathology(@Param("userId") int userId,
                                                    @Param("pathologyId") int pathologyId);
}