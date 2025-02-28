package com.project.spring_project.repository;

import com.project.spring_project.entity.Activity;
import com.project.spring_project.entity.Evaluation;
import com.project.spring_project.entity.EvaluationId;
import com.project.spring_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, EvaluationId> {

    List<Evaluation> findByActivityAndUser(Activity activity, User user);

}
