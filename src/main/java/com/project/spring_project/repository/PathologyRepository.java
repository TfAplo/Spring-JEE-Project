package com.project.spring_project.repository;

import com.project.spring_project.entity.Pathology;
import com.project.spring_project.entity.User;
import com.project.spring_project.entity.User_Pathology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PathologyRepository extends JpaRepository<Pathology, Integer> {
    Optional<Pathology> findByDescription(String description);

}