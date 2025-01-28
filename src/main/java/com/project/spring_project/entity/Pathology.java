package com.project.spring_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Pathology {
    @Id
    private int id_pathology;
    private String description;

    @OneToMany(mappedBy = "pathology")
    private List<Activity_Pathology> activities;

    @OneToMany(mappedBy = "pathology")
    private List<User_Pathology> users;

    public int getId_pathology() {
        return id_pathology;
    }

    public void setId_pathology(int id_pathology) {
        this.id_pathology = id_pathology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Activity_Pathology> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity_Pathology> activities) {
        this.activities = activities;
    }

    public List<User_Pathology> getUsers() {
        return users;
    }

    public void setUsers(List<User_Pathology> users) {
        this.users = users;
    }
}
