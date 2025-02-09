package com.project.spring_project.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_program;
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "program")
    private List<Activity_Program> activities;

    public int getId_program() {
        return id_program;
    }

    public void setId_program(int id_program) {
        this.id_program = id_program;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Activity_Program> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity_Program> activities) {
        this.activities = activities;
    }
}


