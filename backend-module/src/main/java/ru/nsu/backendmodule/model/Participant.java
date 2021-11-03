package ru.nsu.backendmodule.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "participant_role_id")
    private ParticipantRole participantRole;

    @Column(name = "accepted_at")
    private Instant acceptedAt;

    public Participant() {
    }

    public Participant(User user, Project project, ParticipantRole participantRole) {
        this.user = user;
        this.project = project;
        this.participantRole = participantRole;
        this.acceptedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ParticipantRole getProjectRole() {
        return participantRole;
    }

    public void setProjectRole(ParticipantRole projectRole) {
        this.participantRole = projectRole;
    }

    public Instant getAcceptedAt() {
        return acceptedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(project, that.project) && Objects.equals(participantRole, that.participantRole) && Objects.equals(acceptedAt, that.acceptedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
