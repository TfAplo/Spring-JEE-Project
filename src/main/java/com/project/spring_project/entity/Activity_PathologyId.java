package com.project.spring_project.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Activity_PathologyId {
    private int id_pathology;
    private int id_activity;

    public Activity_PathologyId(){}

    public Activity_PathologyId(int id_pathology, int id_activity) {
        this.id_pathology = id_pathology;
        this.id_activity = id_activity;
    }

    public int getId_pathology() {
        return id_pathology;
    }

    public void setId_pathology(int id_pathology) {
        this.id_pathology = id_pathology;
    }

    public int getId_activity() {
        return id_activity;
    }

    public void setId_activity(int id_activity) {
        this.id_activity = id_activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity_PathologyId that = (Activity_PathologyId) o;
        return Objects.equals(id_activity, that.id_activity) && Objects.equals(id_pathology, that.id_pathology);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_activity, id_pathology);
    }
}
