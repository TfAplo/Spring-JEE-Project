package com.project.spring_project.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EvaluationId implements Serializable {
    private int idActivity;
    private int idUser;

    public EvaluationId() {}

    public EvaluationId(int idActivity, int idUser) {
        this.idActivity = idActivity;
        this.idUser = idUser;
    }

    // Getters et Setters
    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluationId that = (EvaluationId) o;
        return Objects.equals(idActivity, that.idActivity) && Objects.equals(idUser, that.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActivity, idUser);
    }
}
