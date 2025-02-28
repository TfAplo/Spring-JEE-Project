package com.project.spring_project.repository;

import com.project.spring_project.entity.Program;
import com.project.spring_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("SELECT p FROM Program p WHERE p.user = :user")
    List<Program> findByUser(@Param("user") User user);
}
