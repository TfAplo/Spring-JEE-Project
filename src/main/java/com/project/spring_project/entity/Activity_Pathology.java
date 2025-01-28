package com.project.spring_project.entity;

import jakarta.persistence.*;

@Entity
public class Activity_Pathology {

    @EmbeddedId
    Activity_PathologyId id;

    @ManyToOne
    @MapsId("id_activity")
    @JoinColumn(name = "id_activity")
    private Activity activity;

    @ManyToOne
    @MapsId("id_pathology")
    @JoinColumn(name = "id_pathology")
    private Pathology pathology;

    public Activity_PathologyId getId() {
        return id;
    }

    public void setId(Activity_PathologyId id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Pathology getPathology() {
        return pathology;
    }

    public void setPathology(Pathology pathology) {
        this.pathology = pathology;
    }
}
