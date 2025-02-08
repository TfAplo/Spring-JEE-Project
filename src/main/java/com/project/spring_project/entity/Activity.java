package com.project.spring_project.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_activity;
    private String description;
    private String name;

    @OneToMany(mappedBy = "activity")
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "activity")
    private List<Activity_Pathology> pathologies;

    @OneToMany(mappedBy = "activity")
    private List<Activity_Program> programs;

    @Transient
    private double averageRate;

    public int getId_activity() {
        return id_activity;
    }

    public void setId_activity(int id_activity) {
        this.id_activity = id_activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Activity_Pathology> getPathologies() {
        return pathologies;
    }

    public void setPathologies(List<Activity_Pathology> pathologies) {
        this.pathologies = pathologies;
    }

    public List<Activity_Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Activity_Program> programs) {
        this.programs = programs;
    }

    public double getAverageRate() {
        return averageRate;
    }
    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }
}
