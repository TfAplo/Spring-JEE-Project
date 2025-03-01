package com.project.spring_project.repository;

import com.project.spring_project.entity.Activity_Program;
import com.project.spring_project.entity.Activity_ProgramId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityProgramRepository extends JpaRepository<Activity_Program, Activity_ProgramId> {
}
