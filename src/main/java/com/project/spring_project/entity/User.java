package com.project.spring_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class User {
    @Id
    private int id_user;
    private String username;
    private String password;
    private int age;
    @OneToMany(mappedBy = "user")
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "user")
    private List<User_Pathology> pathologies;

    // Getters et setters
    public int getId() {
        return id_user;
    }

    public void setId(int id) {
        this.id_user = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<User_Pathology> getPathologies() {
        return pathologies;
    }

    public void setPathologies(List<User_Pathology> pathologies) {
        this.pathologies = pathologies;
    }
}
