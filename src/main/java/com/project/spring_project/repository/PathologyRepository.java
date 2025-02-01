package com.project.spring_project.repository;

import com.project.spring_project.entity.Pathology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PathologyRepository extends JpaRepository<Pathology, Integer> {
    Optional<Pathology> findByDescription(String description);
}