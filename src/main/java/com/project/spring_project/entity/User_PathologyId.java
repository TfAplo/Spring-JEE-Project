package com.project.spring_project.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class User_PathologyId {
    private int id_user;
    private int id_pathology;

    public User_PathologyId() {}

    public User_PathologyId(int user_id, int pathology_id) {
        this.id_user = user_id;
        this.id_pathology = pathology_id;
    }

    public int getUser_id() {
        return id_user;
    }

    public void setUser_id(int user_id) {
        this.id_user = user_id;
    }

    public int getPathology_id() {
        return id_pathology;
    }

    public void setPathology_id(int pathology_id) {
        this.id_pathology = pathology_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User_PathologyId that = (User_PathologyId) o;
        return id_user == that.id_user &&
                id_pathology == that.id_pathology;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_user, id_pathology);
    }
}
