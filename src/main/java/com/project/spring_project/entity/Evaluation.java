package com.project.spring_project.entity;

import jakarta.persistence.*;

@Entity
public class Evaluation {

    @EmbeddedId
    private EvaluationId id; // Clé primaire composite

    @ManyToOne
    @MapsId("idActivity") // Liaison avec l'idActivity dans EvaluationId
    @JoinColumn(name = "id_activity")
    private Activity activity;

    @ManyToOne
    @MapsId("idUser") // Liaison avec l'idUser dans EvaluationId
    @JoinColumn(name = "id_user")
    private User user;

    private double rate;

    public EvaluationId getId() {
        return id;
    }

    public void setId(EvaluationId id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
}
