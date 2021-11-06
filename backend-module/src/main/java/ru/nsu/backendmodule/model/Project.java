package ru.nsu.backendmodule.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "project")
public class Project extends BaseModel<String> {
    @Column(name = "title")
    private String title;

    @Column(name = "pre_description")
    private String preDescription;

    @Column(name = "description")
    private String description;

    @Column(name = "max_participants_count")
    private Integer maxParticipantsCount;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    @OneToMany(mappedBy = "project")
    private final List<ParticipantRole> participantRoles = new ArrayList<>();

    public Project() {
    }

    public Project(String title, String preDescription, String description, User createdBy, Integer max) {
        this.title = title;
        if (preDescription != null) {
            this.preDescription = preDescription;
        }
        if (description != null) {
            this.description = description;
        }
        this.createdBy = createdBy;
        this.createdAt = Instant.now();
        if (max != null) {
            this.maxParticipantsCount = max;
        }
    }

    public Project(String title, User createdBy, Integer max) {
        this(title, null, null, createdBy, max);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public List<ParticipantRole> getParticipantRoles() {
        return participantRoles;
    }

    public void addParticipantRole(ParticipantRole participantRole) {
        if (!participantRoles.contains(participantRole)) {
            participantRoles.add(participantRole);
        }
    }

    public Integer getMaxParticipantsCount() {
        return maxParticipantsCount;
    }

    public void setMaxParticipantsCount(Integer maxParticipantsCount) {
        this.maxParticipantsCount = maxParticipantsCount;
    }

    public String getPreDescription() {
        return preDescription;
    }

    public void setPreDescription(String preDescription) {
        this.preDescription = preDescription;
    }

    public boolean isProjectOwner(String userId) {
        //Important - we MUST call methods here and NOT use fields
        //Otherwise this method crashes if called before all other
        //When the spring proxy is not yet initialized
        //and all the fields are nulls
        return Objects.equals(userId, getCreatedBy().getId());
    }

    //TODO
    public boolean isParticipant(Participant participant) {
        return participantRoles
                .stream()
                .anyMatch(participantRole ->
                        participantRole
                                .getParticipants()
                                .stream()
                                .anyMatch(participant::equals)
                );
    }
}
