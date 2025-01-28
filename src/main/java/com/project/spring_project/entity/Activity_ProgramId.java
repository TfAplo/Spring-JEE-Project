package com.project.spring_project.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Activity_ProgramId {
    private int id_program;
    private int id_activity;

    public Activity_ProgramId() {}

    public Activity_ProgramId(int id_program, int id_activity) {
        this.id_program = id_program;
        this.id_activity = id_activity;
    }

    public int getId_program() {
        return id_activity;
    }

    public void setId_program(int id_program) {
        this.id_program = id_program;
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
        Activity_ProgramId that = (Activity_ProgramId) o;
        return Objects.equals(id_activity, that.id_activity) && Objects.equals(id_program, that.id_program);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_activity, id_program);
    }
}
