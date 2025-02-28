package com.project.spring_project.entity;

import jakarta.persistence.*;

@Entity
public class User_Pathology {
    @EmbeddedId
    User_PathologyId id = new User_PathologyId();

    @ManyToOne
    @MapsId("id_pathology")
    @JoinColumn(name = "id_pathology")
    private Pathology pathology;

    @ManyToOne
    @MapsId("id_user")
    @JoinColumn(name = "id_user")
    private User user;

    public User_PathologyId getId() {
        return id;
    }

    public void setId(User_PathologyId id) {
        this.id = id;
    }

    public Pathology getPathology() {
        return pathology;
    }

    public void setPathology(Pathology pathology) {
        this.pathology = pathology;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
