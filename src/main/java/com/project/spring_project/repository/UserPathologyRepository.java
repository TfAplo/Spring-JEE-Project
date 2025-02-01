package com.project.spring_project.repository;

import com.project.spring_project.entity.User_Pathology;
import com.project.spring_project.entity.User_PathologyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPathologyRepository extends JpaRepository<User_Pathology, User_PathologyId> {
}