package com.project.spring_project.repository;

import com.project.spring_project.entity.Evaluation;
import com.project.spring_project.entity.EvaluationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, EvaluationId> {
}
