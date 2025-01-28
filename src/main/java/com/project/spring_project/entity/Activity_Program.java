package com.project.spring_project.entity;

import jakarta.persistence.*;

@Entity
public class Activity_Program {
    @EmbeddedId
    private Activity_ProgramId id;

    @ManyToOne
    @MapsId("id_activity")
    @JoinColumn(name = "id_activity")
    private Activity activity;

    @ManyToOne
    @MapsId("id_program")
    @JoinColumn(name = "id_program")
    private Program program;

    public Activity_ProgramId getId() {
        return id;
    }

    public void setId(Activity_ProgramId id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
