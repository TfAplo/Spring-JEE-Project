package com.project.spring_project.repository;

import com.project.spring_project.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
